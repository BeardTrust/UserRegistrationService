package com.beardtrust.webapp.userservice.controllers;

import com.beardtrust.webapp.userservice.models.RegistrationResponse;
import com.beardtrust.webapp.userservice.models.UserRegistration;
import com.beardtrust.webapp.userservice.services.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Consumes;

/**
 * The type User registration controller.
 *
 * @author Matthew Crowell <Matthew.Crowell@Smoothstack.com>
 */
@RestController
@RequestMapping(path = "/users")
public class UserRegistrationController {

	private final UserRegistrationService userRegistrationService;

	/**
	 * Instantiates a new User registration controller.
	 *
	 * @param userRegistrationService the user registration service
	 */
	@Autowired
	public UserRegistrationController(UserRegistrationService userRegistrationService) {
		this.userRegistrationService = userRegistrationService;
	}

	/**
	 * Processes an incoming request to register a new user and routes that request to
	 * the user registration service.
	 *
	 * @param body UserRegistration the user registration request data
	 * @return ResponseEntity<UserDTO> the new user data and http status code
	 */
	@PostMapping
	@Consumes({MediaType.ALL_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<RegistrationResponse> registerUser(@Valid @RequestBody UserRegistration body) {
		ResponseEntity<RegistrationResponse> response = null;
		RegistrationResponse registrationResponse = new RegistrationResponse();
		registrationResponse.setUserId(userRegistrationService.registerUser(body));
		if (registrationResponse.getUserId() != null && registrationResponse.getUserId().length() > 0) {
			response = new ResponseEntity<>(registrationResponse, HttpStatus.CREATED);
		}

		return response;
	}
}