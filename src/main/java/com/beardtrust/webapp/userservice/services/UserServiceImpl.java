package com.beardtrust.webapp.userservice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.beardtrust.webapp.userservice.dtos.UserDTO;
import com.beardtrust.webapp.userservice.exceptions.DuplicateEntryException;
import com.beardtrust.webapp.userservice.models.UserRegistration;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beardtrust.webapp.userservice.entities.UserEntity;
import com.beardtrust.webapp.userservice.repos.UserRepository;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public String registerUser(UserRegistration userRegistration) {
		UserEntity userEntity = searchRepositoryForDuplicates(userRegistration);

		UserDTO userDTO = null;

		if (userEntity == null) {
			log.info("Attempting to save user " + userRegistration.getUsername() + " to database");
			try {
				userDTO = createUser(userRegistration);
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
	 * This method creates a UserEntity object from the provided UserRegistration, saves that UserEntity to the
	 * database and then returns a UserDTO created from the saved UserEntity.
	 *
	 * @param userRegistration UserRegistration the registration data sent to the server
	 * @return UserDTO a new UserDTO for the UserEntity saved in the database
	 */
	private UserDTO createUser(UserRegistration userRegistration) {
		userRegistration.setPassword(passwordEncoder.encode(userRegistration.getPassword()));
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		UserEntity userEntity = modelMapper.map(userRegistration, UserEntity.class);
		userEntity.setUserId(UUID.randomUUID().toString());
		userEntity.setRole("user");

		return modelMapper.map(userRepository.save(userEntity), UserDTO.class);
	}

	/**
	 * This method searches for a user in the database, locating any user with associated fields
	 * that are required to be unique, including email address, username, and phone number.
	 *
	 * @param userRegistration UserRegistration the user registration details
	 * @return UserEntity a UserEntity object representing the user as found in the database
	 */
	private UserEntity searchRepositoryForDuplicates(UserRegistration userRegistration) {
		UserEntity userEntity;
		userEntity = userRepository.findByEmail(userRegistration.getEmail());

		if (userEntity == null) {
			userEntity = userRepository.findByUsername(userRegistration.getUsername());
		}

		if (userEntity == null) {
			userEntity = userRepository.findByPhone(userRegistration.getPhone());
		}
		return userEntity;
	}

	/**
	 * This method throws an appropriate duplicate entry exception.
	 *
	 * @param userRegistration UserRegistration the user registration object
	 * @param userEntity       UserEntity the user entity found in the database
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

		if (registrationEmail.equals(entityEmail) && registrationUsername.equals(entityUsername) &&
				registrationPhone.equals(entityPhone)) {
			log.error(String.format("User with email of '%s', username of '%s', and phone number of '%s' already" +
					" exists in database", registrationEmail, registrationUsername, registrationPhone));
			throw new DuplicateEntryException("User with this email, username, and phone number " +
					"already exists");
		}

		if (registrationEmail.equals(entityEmail)) {
			log.error(String.format("Email address '%s' already present in database", entityEmail));
			throw new DuplicateEntryException(String.format("email address '%s' already registered",
					entityEmail));
		}

		if (registrationUsername.equals(entityUsername)) {
			log.error(String.format("Username '%s' already present in database", entityUsername));
			throw new DuplicateEntryException(String.format("username '%s' already registered",
					entityUsername));
		}

		if (registrationPhone.equals(entityPhone)) {
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

	@Override
	@Transactional
	public List<UserEntity> getAll() {
		
		List<UserEntity> list = userRepository.findAll();
		
		return list;
	}

	@Override
	@Transactional
	public UserEntity getById(String id) {
  
		Optional<UserEntity> result = userRepository.findById(id);
		
		UserEntity user = null;
		
		if(result.isPresent()) {
			user = result.get();
		}
		else {
			throw new RuntimeException("User id - " + id + " not found");
		}
		
		return user;
	}

	@Override
	@Transactional
	public void deleteById(String id) {
		
		Optional<UserEntity> result = userRepository.findById(id);
		
		if(result.isPresent()) {
			userRepository.deleteById(id);
		}
		else {
			throw new RuntimeException("User id - " + id + " not found");
		}
	}

	@Override
	@Transactional
	public void save(UserEntity user) {
		userRepository.save(user);
		}

	public String updateService(UserEntity u, String id) {
		Optional<UserEntity> ouser = userRepository.findById(id);
		if (ouser.isPresent()) {
			u.setUserId(ouser.get().getUserId());
			u.setRole(ouser.get().getRole());
			userRepository.save(u);
			return "Update complete!";
		} else {
			return "Entity not found!";
		}

	}
	//Admin Access required

	public List<UserEntity> getAllUserInfos() {
		return userRepository.findAll();
	}

	public Optional<UserEntity> getSpecificUserInfos(String account_id) {
		return userRepository.findById(account_id);
	}

}