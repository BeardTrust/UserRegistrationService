package com.beardtrust.webapp.userservice.services;

import com.beardtrust.webapp.userservice.dtos.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * The interface Authentication service.
 *
 * @author Matthew Crowell <Matthew.Crowell@Smoothstack.com>
 */
public interface AuthenticationService extends UserDetailsService {
	/**
	 * Gets user details by email.
	 *
	 * @param email the email
	 * @return the user details by email
	 */
	UserDTO getUserDetailsByEmail(String email);
}