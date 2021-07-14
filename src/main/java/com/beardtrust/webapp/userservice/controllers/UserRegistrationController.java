package com.beardtrust.webapp.userservice.controllers;

import com.beardtrust.webapp.userservice.dtos.UserDTO;
import com.beardtrust.webapp.userservice.models.UserRegistration;
import com.beardtrust.webapp.userservice.services.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@CrossOrigin
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
	public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserRegistration body) {
		ResponseEntity<UserDTO> response;
		UserDTO user = userRegistrationService.registerUser(body);
		if (user.getUserId() != null) {
			response = new ResponseEntity<>(user, HttpStatus.CREATED);
		} else {
			response = new ResponseEntity<>(user, HttpStatus.CONFLICT);
		}
		return response;
	}

	/**
	 * Display users response entity.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@PreAuthorize("hasAuthority('admin') or principal == #id")
	@GetMapping(path = "/id/{id}")
	public ResponseEntity<UserDTO> displayUsers(@PathVariable("id") String id) {
		ResponseEntity<UserDTO> response = null;
		UserDTO userDetails = userRegistrationService.displayUser(id);
		if (!userDetails.getUserId().equals("")) {
			response = new ResponseEntity<>(userDetails, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(userDetails, HttpStatus.I_AM_A_TEAPOT);
		}
		return response;
	}
}