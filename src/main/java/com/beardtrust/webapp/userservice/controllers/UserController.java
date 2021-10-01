package com.beardtrust.webapp.userservice.controllers;

import com.beardtrust.webapp.userservice.entities.UserEntity;
import com.beardtrust.webapp.userservice.models.RegistrationResponse;
import com.beardtrust.webapp.userservice.models.UserRegistration;
import com.beardtrust.webapp.userservice.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * This class handles the mapping of http requests to the user service.
 *
 * @author Matthew Crowell <Matthew.Crowell@Smoothstack.com>
 * @author Nathanael Grier
 * @author Davis Hill
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/")
@Slf4j
public class UserController {

	private final UserService userService;

	/**
	 * This is the autowired constructor for the user controller class.
	 *
	 * @param userService UserService the implementation of the user service
	 */
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
        @PreAuthorize("permitAll()")
	@GetMapping(path = "/health")
        @Consumes({MediaType.ALL_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
        public ResponseEntity<String> healthCheck() {
            log.info("Health Check Incoming");
            return new ResponseEntity<>("Healthy", HttpStatus.OK);
        }

	/**
	 * Processes an incoming request to register a new user and routes that request to
	 * the user registration service.
	 *
	 * @param body UserRegistration the user registration request data
	 * @return ResponseEntity<UserDTO> the new user data and http status code
	 */
	@PostMapping(path = "/users")
	@Consumes({MediaType.ALL_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<RegistrationResponse> registerUser(@Valid @RequestBody UserRegistration body) {
		ResponseEntity<RegistrationResponse> response = null;
		RegistrationResponse registrationResponse = new RegistrationResponse();
		registrationResponse.setUserId(userService.registerUser(body));
		if (registrationResponse.getUserId() != null && registrationResponse.getUserId().length() > 0) {
			response = new ResponseEntity<>(registrationResponse, HttpStatus.CREATED);
		}

		return response;
	}

	@PostMapping(path = "/admin/users")
	@PreAuthorize("hasAuthority('admin')")
	public void createUser(@RequestBody UserEntity user) {
		userService.save(user);
	}


	@PreAuthorize("hasAuthority('admin')")
	@GetMapping(path = "/admin/users")
	public Map<String, Object> findPaginated(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam(value = "sort", required = false) String sort, @RequestParam(value="asc", required = false) boolean asc, @RequestParam(value="search", required = false) String search) {

		Page<UserEntity> resultPage;
		if (sort == null) {
			resultPage = userService.findPaginated(PageRequest.of(page, size), search);
		} else {
			resultPage = userService.findPaginated(PageRequest.of(page, size, Sort.by(asc ? Sort.Direction.ASC : Sort.Direction.DESC, sort)), search);
		}

		HashMap<String, Object> map = new HashMap<>();
		map.put("totalElements", resultPage.getTotalElements());
		map.put("totalPages", resultPage.getTotalPages());
		map.put("pageNumber", resultPage.getNumber());
		map.put("content", resultPage.getContent());
		return map;
	}

	@GetMapping("/admin/users/{id}")
	@PreAuthorize("hasAuthority('admin') or principal == #id")
	public UserEntity displayUserById(@PathVariable String id) {
		return userService.getById(id);
	}


	@PutMapping("admin/users/{id}")
	@PreAuthorize("hasAuthority('admin')")
	public void updateUserByAdmin(@RequestBody UserEntity user, @PathVariable String id) {

		userService.save(user);
	}

	@DeleteMapping("/admin/users/{id}")
	@PreAuthorize("hasAuthority('admin') or principal == #id")
	public void deleteUser(@PathVariable String id) {
		userService.deleteById(id);
	}

	@PreAuthorize("hasAuthority('admin') or principal == #id")
	@PutMapping("/users/{id}")
	public String updateUser(@RequestBody UserEntity u, @PathVariable String id) {
		return userService.updateService(u, id);
	}

	@PreAuthorize("hasAuthority('admin')")
	@GetMapping
	public List<UserEntity> getAllUserInfos() {
		return userService.getAllUserInfos();
	}

	@PreAuthorize("hasAuthority('admin') or principal == #id")
	@GetMapping("/users/{id}")
	public Optional<UserEntity> getSpecificUserInfos(@PathVariable String id) {
		return userService.getSpecificUserInfos(id);
	}
}
