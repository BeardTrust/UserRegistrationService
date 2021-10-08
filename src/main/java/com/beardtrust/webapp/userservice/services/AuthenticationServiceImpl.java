package com.beardtrust.webapp.userservice.services;

import com.beardtrust.webapp.userservice.dtos.UserDTO;
import com.beardtrust.webapp.userservice.entities.UserEntity;
import com.beardtrust.webapp.userservice.repos.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Authentication service.
 *
 * @author Matthew Crowell <Matthew.Crowell@Smoothstack.com>
 */
@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
	private final Environment environment;
	private final UserRepository repository;

	/**
	 * Instantiates a new Authentication service.
	 *
	 * @param environment the environment
	 * @param repository  the repository
	 */
	@Autowired
	public AuthenticationServiceImpl(Environment environment, UserRepository repository) {
		log.info("Creating authentication service");
		this.environment = environment;
		this.repository = repository;
	}

	@Override
	public UserDTO getUserDetailsByEmail(String email) {
            log.trace("Authentication searching by email...");
            log.debug("Email searched by: " + email);
		UserEntity userEntity = repository.findByEmail(email);

		if (userEntity == null) {
                    log.warn("Authentication couldn't find user by email!!!");
			throw new UsernameNotFoundException(email);
		}

		ModelMapper modelMapper = new ModelMapper();
		UserDTO auth = modelMapper.map(userEntity, UserDTO.class);
                log.trace("Returning Authenticated user DTO...");
                log.debug("UserDTO created: " + auth);
                return auth;
	}

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
            log.trace("Authentication loading by username...");
            log.debug("Username searched by: " + s);
		UserEntity userEntity = repository.findByEmail(s);

		if (userEntity == null) {
                    log.error("User not found!!!");
                    throw new UsernameNotFoundException(s);
                }

		List<GrantedAuthority> authorities = new ArrayList<>();
		SimpleGrantedAuthority role = new SimpleGrantedAuthority(userEntity.getRole());
		authorities.add(role);
                log.trace("Authorities granted. Returning user details...");
		return new User(userEntity.getEmail(), userEntity.getPassword(), true, true, true, true, authorities);
	}
}