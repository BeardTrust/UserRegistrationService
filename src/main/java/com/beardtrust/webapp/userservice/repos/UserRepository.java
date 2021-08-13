package com.beardtrust.webapp.userservice.repos;

import com.beardtrust.webapp.userservice.entities.UserEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface UserEntity repository.
 *
 * @author Matthew Crowell <Matthew.Crowell@Smoothstack.com>
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

	/**
	 * Find by email user entity.
	 *
	 * @param s the s
	 * @return the user entity
	 */
	UserEntity findByEmail(String s);

	/**
	 * Find a user entity by phone number
	 *
	 * @param s String phone number
	 * @return UserEntity user entity
	 */
	UserEntity findByPhone(String s);

	/**
	 * Find a user entity by username.
	 *
	 * @param s String username
	 * @return the user entity
	 */
	UserEntity findByUsername(String s);

	/**
	 * Find all user entities
	 *
	 * @param pageable Pageable paging information
	 * @return the user entities
	 */
	Page<UserEntity> findAll(Pageable pageable);

	/**
	 * Find all user entities with any field containing (ignoring case) the respective specified string
	 *
	 * @param firstName String first name
	 * @param lastName String last name
	 * @param username String username
	 * @param email String email
	 * @param phone String phone number
	 * @param pageable Pageable paging information
	 * @return the user entities
	 */
	Page<UserEntity> findAllByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCaseOrUsernameContainsIgnoreCaseOrEmailContainsIgnoreCaseOrPhoneContainsIgnoreCase(String firstName, String lastName, String username, String email, String phone, Pageable pageable);
}
