package com.beardtrust.webapp.userservice.security;

import com.beardtrust.webapp.userservice.dtos.UserDTO;
import com.beardtrust.webapp.userservice.models.LoginRequestModel;
import com.beardtrust.webapp.userservice.services.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The type Authentication filter.
 *
 * @author Matthew Crowell <Matthew.Crowell@Smoothstack.com>
 */
@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationService authenticationService;
	private final Environment environment;
	private final String ROLE_REQUEST_MISMATCH = "Role does not match authentication role request";

	/**
	 * Instantiates a new Authentication filter.
	 *
	 * @param authenticationService the authentication service
	 * @param environment           the environment
	 * @param authenticationManager the authentication manager
	 */
	public AuthenticationFilter(AuthenticationService authenticationService, Environment environment,
								AuthenticationManager authenticationManager) {
		log.info("Creating authentication filter");
                log.trace("Creating authentication filter");
		this.authenticationService = authenticationService;
		this.environment = environment;
		super.setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
            log.trace("Attempting Authentication...");
		Authentication auth = null;

		try {
			LoginRequestModel credentials = new ObjectMapper().readValue(request.getInputStream(),
					LoginRequestModel.class);

			log.info("Attempting to authenticate user " + credentials.getEmail());
                        log.trace("Attempting to authenticate user...");
                        log.debug("User email authenticating: " + credentials.getEmail());

			List<GrantedAuthority> authorities = new ArrayList<>();

			UserDTO userDTO = authenticationService.getUserDetailsByEmail(credentials.getEmail());

			UsernamePasswordAuthenticationToken token = null;

			if(request.getHeader("LR-Type").equals(userDTO.getRole())){
                            log.trace("LR-Type found...");
				if (userDTO.getRole().equals("admin")) {
                                    log.trace("LR-Type admin...");
					SimpleGrantedAuthority admin = new SimpleGrantedAuthority("admin");
					authorities.add(admin);
				} else if (userDTO.getRole().equals("user")) {
                                    log.trace("LR-Type user...");
					SimpleGrantedAuthority user = new SimpleGrantedAuthority("user");
					authorities.add(user);
				}
                                log.trace("Creating token...");
				token = new UsernamePasswordAuthenticationToken(credentials.getEmail(),
						credentials.getPassword(), authorities);
                                log.debug("Token created: " + token);
			} else {
                            log.warn("LR-Type not found...");
				token = new UsernamePasswordAuthenticationToken(ROLE_REQUEST_MISMATCH, ROLE_REQUEST_MISMATCH, authorities);
			}
                        log.trace("Creating authentication...");
			auth = getAuthenticationManager().authenticate(token);
                        log.debug("Authentication created: " + auth);
		} catch (IOException e) {
			log.error("IOException thrown during authentication attempt");
			throw new RuntimeException(e);
		}
                log.trace("Returning authentication...");
		return auth;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
            log.trace("Authentication successful...");
		String username = ((User) authResult.getPrincipal()).getUsername();
                log.debug("Username authenticated: " + username);

		UserDTO userDetails = null;

		try {
                    log.trace("Trying to get authentication by username...");
			userDetails = authenticationService.getUserDetailsByEmail(username);
		} catch (UsernameNotFoundException e) {
			log.error("User not found!!!");
		}
                log.trace("Building token...");
		String token = Jwts.builder()
				.setSubject(userDetails.getId())
				.setExpiration(new Date(System.currentTimeMillis() +
						Long.parseLong(environment.getProperty("token.expiration"))))
				.signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
				.compact();
                log.debug("Token created: " + token);

		response.addHeader("Authorization", environment.getProperty("authorization.token.header.prefix") + " " + token);
        response.addHeader("BTUID", userDetails.getId());
		log.info("User " + userDetails.getEmail() + " authenticated");
                log.trace("User authenticated...");
                log.debug("Email authenticayed: " + userDetails.getEmail());
	}
}
