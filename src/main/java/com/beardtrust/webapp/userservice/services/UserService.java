package com.beardtrust.webapp.userservice.services;

import java.util.List;
import com.beardtrust.webapp.userservice.entities.UserEntity;

public interface UserService {
	
	public List<UserEntity> getAll();
	
	public UserEntity getById(String id);
	
	public void deleteById(String id);
	
	public void save(UserEntity user);

}
