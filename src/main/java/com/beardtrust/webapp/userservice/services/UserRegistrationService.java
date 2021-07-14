package com.beardtrust.webapp.userservice.services;

import com.beardtrust.webapp.userservice.dtos.UserDTO;
import com.beardtrust.webapp.userservice.models.UserRegistration;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * The interface UserEntity registration service.
 *
 * @author Matthew Crowell <Matthew.Crowell@Smoothstack.com>
 */
public interface UserRegistrationService extends UserDetailsService {

	/**
	 * Register user user.
	 *
	 * @param user the user
	 * @return the user
	 */
	UserDTO registerUser(UserRegistration user);


	/**
	 * Display user user dto.
	 *
	 * @param userId the user id
	 * @return the user dto
	 */
	UserDTO displayUser(String userId);


	/**
	 * Gets user details by email.
	 *
	 * @param username the username
	 * @return the user details by email
	 */
	UserDTO getUserDetailsByEmail(String username);
}
