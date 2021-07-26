package com.beardtrust.webapp.userservice.services;

import java.util.List;
import com.beardtrust.webapp.userservice.entities.UserEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
	
	public List<UserEntity> getAll();
	
	public UserEntity getById(String id);
	
	public void deleteById(String id);
	
	public void save(UserEntity user);

    public Page<UserEntity> findPaginated(Pageable pageable, String search);

}
