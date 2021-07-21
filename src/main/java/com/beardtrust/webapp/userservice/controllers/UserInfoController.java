package com.beardtrust.webapp.userservice.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.beardtrust.webapp.userservice.entities.UserEntity;
import com.beardtrust.webapp.userservice.repos.UserRepository;
import com.beardtrust.webapp.userservice.services.UserInfoService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Nathanael <Nathanael.Grier at your.org>
 */
@RequestMapping(path = "/users")
@RestController
@CrossOrigin(origins = "*")
public class UserInfoController {

	@Autowired
	private UserRepository repo;
	@Autowired
	private UserInfoService us = new UserInfoService(repo);

	@PreAuthorize("hasAuthority('admin') or principal == #id")
	@PutMapping("/{id}")
	public String updateUser(@RequestBody UserEntity u, @PathVariable String id) {
		return us.updateService(u, id);
	}

	@PreAuthorize("hasAuthority('admin')")
	@GetMapping
	public List<UserEntity> getAllUserInfos() {
		return us.getAllUserInfos();
	}

	@PreAuthorize("hasAuthority('admin') or principal == #id")
	@GetMapping("/{id}")
	public Optional<UserEntity> getSpecificUserInfos(@PathVariable String id) {
		return us.getSpecificUserInfos(id);
	}
}