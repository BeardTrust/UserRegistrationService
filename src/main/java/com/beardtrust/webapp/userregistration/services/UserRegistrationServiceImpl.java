package com.beardtrust.webapp.userregistration.services;

import com.beardtrust.webapp.userregistration.dtos.UserDTO;
import com.beardtrust.webapp.userregistration.entities.UserEntity;
import com.beardtrust.webapp.userregistration.models.UserRegistration;
import com.beardtrust.webapp.userregistration.repos.UserRepository;
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
	public UserDTO registerUser(UserRegistration userRegistration) {
		userRegistration.setPassword(passwordEncoder.encode(userRegistration.getPassword()));
		UserEntity userEntity = userRepository.findByEmail(userRegistration.getEmail());
		UserDTO user = null;
		if(userEntity == null){
			log.info("Attempting to save user " + userRegistration.getUsername() + " to database");
			try {
				ModelMapper modelMapper = new ModelMapper();
				modelMapper.getConfiguration()
						.setMatchingStrategy(MatchingStrategies.STRICT);
				user = modelMapper.map(userRegistration, UserDTO.class);
				userEntity = modelMapper.map(user, UserEntity.class);
				userEntity.setUserId(UUID.randomUUID().toString());
				user = modelMapper.map(userRepository.save(userEntity), UserDTO.class);

			} catch (Exception e) {
				log.error("Failed to save user " + userRegistration.getUsername() + " to database");
			}
			log.info("UserEntity " + userRegistration.getUsername() + " saved to database");
		} else {
			log.error("User " + userRegistration.getUsername() + " cannot be saved due to duplicate " +
					"values in database");
			user = new UserDTO();
		}

		return user;
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

