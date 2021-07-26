package com.beardtrust.webapp.userservice.security;

import com.beardtrust.webapp.userservice.services.AuthenticationService;
import com.beardtrust.webapp.userservice.services.AuthorizationService;
import com.beardtrust.webapp.userservice.services.UserRegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The application's security configuration class.
 *
 * @author Matthew Crowell <Matthew.Crowell@Smoothstack.com>
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final Environment environment;
	private final UserRegistrationService userRegistrationService;
	private final PasswordEncoder passwordEncoder;
	private final AuthorizationService authorizationService;
	private final AuthenticationService authenticationService;

	/**
	 * Instantiates a new Security config.
	 *
	 * @param environment             the environment
	 * @param userRegistrationService
	 * @param passwordEncoder
	 * @param authorizationService
	 * @param authenticationService
	 */
	@Autowired
	public SecurityConfig(Environment environment, UserRegistrationService userRegistrationService, PasswordEncoder passwordEncoder, AuthorizationService authorizationService, AuthenticationService authenticationService) {
		this.environment = environment;
		this.userRegistrationService = userRegistrationService;
		this.passwordEncoder = passwordEncoder;
		this.authorizationService = authorizationService;
		this.authenticationService = authenticationService;
	}

	@Description("Configure HTTP Security")
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.cors()
				.and().authorizeRequests()
				.antMatchers(HttpMethod.POST, "/users").permitAll()
				.antMatchers(HttpMethod.POST, "/login").permitAll()
				.antMatchers("/h2-console/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.addFilter(new AuthorizationFilter(authenticationManager(), environment, authorizationService))
				.addFilter(getAuthenticationFilter());
		http.headers().frameOptions().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userRegistrationService).passwordEncoder(passwordEncoder);
	}

	private AuthenticationFilter getAuthenticationFilter() throws Exception {
		AuthenticationFilter filter = new AuthenticationFilter(authenticationService, environment, authenticationManager());
		filter.setFilterProcessesUrl(environment.getProperty("login.url.path"));

		return filter;
	}
}
