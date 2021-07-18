package com.beardtrust.webapp.userservice.services;

import com.beardtrust.webapp.userservice.dtos.UserDTO;

import com.beardtrust.webapp.userservice.entities.UserEntity;
import com.beardtrust.webapp.userservice.exceptions.DuplicateEntryException;
import com.beardtrust.webapp.userservice.models.UserRegistration;
import com.beardtrust.webapp.userservice.repos.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

/**
 * The type UserEntity registration service.
 *
 * @author Matthew Crowell <Matthew.Crowell@Smoothstack.com>
 */
@Service
@Slf4j
public class UserRegistrationServiceImpl implements UserRegistrationService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	/**
	 * Instantiates a new UserEntity registration service.
	 *
	 * @param userRepository  the user repository
	 * @param passwordEncoder the password encoder
	 */
	@Autowired
	public UserRegistrationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public String registerUser(UserRegistration userRegistration) {
		userRegistration.setPassword(passwordEncoder.encode(userRegistration.getPassword()));

		UserEntity userEntity = userRepository.findByEmail(userRegistration.getEmail());

		if(userEntity == null) {
			userEntity = userRepository.findByUsername(userRegistration.getUsername());
		}

		if(userEntity == null) {
			userEntity = userRepository.findByPhone(userRegistration.getPhone());
		}

		UserDTO userDTO = null;
		if (userEntity == null) {
			log.info("Attempting to save user " + userRegistration.getUsername() + " to database");
			try {
				ModelMapper modelMapper = new ModelMapper();
				modelMapper.getConfiguration()
						.setMatchingStrategy(MatchingStrategies.STRICT);

				userEntity = modelMapper.map(userRegistration, UserEntity.class);
				userEntity.setUserId(UUID.randomUUID().toString());
				userEntity.setRole("user");

				userDTO = modelMapper.map(userRepository.save(userEntity), UserDTO.class);

				log.info("UserEntity " + userRegistration.getUsername() + " saved to database");
			} catch (Exception e) {
				log.error("Failed to save user " + userRegistration.getUsername() + " to database");
			}
		} else {
			throwDuplicateEntryException(userRegistration, userEntity);
		}

		return userDTO != null ? userDTO.getUserId() : null;
	}

	/**
	 * This method throws an appropriate duplicate entry exception.
	 *
	 * @param userRegistration UserRegistration the user registration object
	 * @param userEntity UserEntity the user entity found in the database
	 */
	private void throwDuplicateEntryException(UserRegistration userRegistration, UserEntity userEntity) {
		log.error("User " + userRegistration.getUsername() + " cannot be saved due to duplicate " +
				"values in database");

		String registrationEmail = userRegistration.getEmail();
		String entityEmail = userEntity.getEmail();

		String registrationUsername = userRegistration.getUsername();
		String entityUsername = userEntity.getUsername();

		String registrationPhone = userRegistration.getPhone();
		String entityPhone = userEntity.getPhone();

		if(registrationEmail.equals(entityEmail) && registrationUsername.equals(entityUsername) &&
				registrationPhone.equals(entityPhone)) {
			log.error(String.format("User with email of '%s', username of '%s', and phone number of '%s' already" +
					" exists in database", registrationEmail, registrationUsername, registrationPhone));
			throw new DuplicateEntryException("User with this email, username, and phone number " +
					"already exists");
		}

		if(registrationEmail.equals(entityEmail)) {
			log.error(String.format("Email address '%s' already present in database", entityEmail));
			throw new DuplicateEntryException(String.format("email address '%s' already registered",
					entityEmail));
		}

		if(registrationUsername.equals(entityUsername)) {
			log.error(String.format("Username '%s' already present in database", entityUsername));
			throw new DuplicateEntryException(String.format("username '%s' already registered",
					entityUsername));
		}

		if(registrationPhone.equals(entityPhone)) {
			log.error(String.format("Phone number '%s' already present in database", entityPhone));
			throw new DuplicateEntryException(String.format("phone number '%s' already registered",
					entityPhone));
		}
	}

	@Override
	public UserDTO displayUser(String userId) {
		log.info("Looking up details for user " + userId);
		Optional<UserEntity> user = userRepository.findById(userId);
		UserDTO userDetails = null;
		if (user.isPresent()) {
			log.info("UserEntity " + user.get().getUserId() + " located in database");
			ModelMapper modelMapper = new ModelMapper();
			userDetails = modelMapper.map(user.get(), UserDTO.class);
		}
		return userDetails;
	}

	@Override
	public UserDTO getUserDetailsByEmail(String username) {
		UserEntity user = userRepository.findByEmail(username);

		if (user == null) throw new UsernameNotFoundException(username);

		return new ModelMapper().map(user, UserDTO.class);
	}

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByEmail(s);

		if (user == null) throw new UsernameNotFoundException(s);

		return new org.springframework.security.core.userdetails.User(user.getEmail(),
				user.getPassword(), true, true,
				true, true, new ArrayList<>());
	}
}

