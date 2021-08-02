package com.beardtrust.webapp.userservice.controllers;

import com.beardtrust.webapp.userservice.entities.UserEntity;
import com.beardtrust.webapp.userservice.services.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Davis Hill
 */

@RestController
@RequestMapping("/admin/users")
public class UserController {
	
	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping()
  @PreAuthorize("hasAuthority('admin')")
	public void createUser(@RequestBody UserEntity user) {
		userService.save(user);
	}
	
	@GetMapping()
	@PreAuthorize("hasAuthority('admin')")
	public List<UserEntity> displayAllUsers(){
		return userService.getAll();
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('admin') or principal == #id")
	public UserEntity displayUserById(@PathVariable String id) {
		return userService.getById(id);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('admin')")
	public void updateUser(@RequestBody UserEntity user, @PathVariable String id) {
		
		userService.save(user);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('admin') or principal == #id")
	public void deleteUser(@PathVariable String id){
		userService.deleteById(id);
	}
  
}
