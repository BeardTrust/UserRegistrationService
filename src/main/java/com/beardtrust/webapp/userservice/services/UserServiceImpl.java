package com.beardtrust.webapp.userservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beardtrust.webapp.userservice.entities.UserEntity;
import com.beardtrust.webapp.userservice.repos.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	private UserRepository userRepo;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	@Transactional
	public List<UserEntity> getAll() {
		
		List<UserEntity> list = userRepo.findAll();
		
		return list;
	}

	@Override
	@Transactional
	public UserEntity getById(String id) {
  
		Optional<UserEntity> result = userRepo.findById(id);
		
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
		
		Optional<UserEntity> result = userRepo.findById(id);
		
		if(result.isPresent()) {
			userRepo.deleteById(id);
		}
		else {
			throw new RuntimeException("User id - " + id + " not found");
		}
	}

	@Override
	@Transactional
	public void save(UserEntity user) {
		userRepo.save(user);
		}

}