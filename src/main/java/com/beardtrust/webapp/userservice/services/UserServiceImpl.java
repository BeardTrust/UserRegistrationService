package com.beardtrust.webapp.userservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beardtrust.webapp.userservice.entities.UserEntity;
import com.beardtrust.webapp.userservice.repos.UserRepository;

import org.springframework.jdbc.object.SqlQuery;

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

	@Override
	public Page<UserEntity> findPaginated(Pageable pageable, String search) {
		Page<UserEntity> page;
		if (search == null) {
			page = userRepo.findAll(pageable);
		} else {
			page = userRepo.findAllByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCaseOrUsernameContainsIgnoreCaseOrEmailContainsIgnoreCaseOrPhoneContainsIgnoreCase(search, search, search, search, search, pageable);
		}
		for (UserEntity user : page) {
			user.setPassword(null);
		}
		return page;
	}
}
