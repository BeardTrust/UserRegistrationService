package com.beardtrust.webapp.userservice.services;

import com.beardtrust.webapp.userservice.dtos.UserDTO;
import com.beardtrust.webapp.userservice.entities.UserEntity;
import com.beardtrust.webapp.userservice.models.UserRegistration;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

	/**
	 * This method registers a user using the UserRegistration object
	 * provided as an argument.
	 *
	 * @param user UserRegistration the user to register
	 * @return String the user's user id
	 */
	String registerUser(UserRegistration user);


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

	List<UserEntity> getAll();

	UserEntity getById(String id);

	void deleteById(String id);

	void save(UserEntity user);

	List<UserEntity> getAllUserInfos();

	Optional<UserEntity> getSpecificUserInfos(String account_id);

	String updateService(UserEntity u, String id);
}
