/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beardtrust.webapp.userservice.services;

import com.beardtrust.webapp.userservice.entities.UserEntity;
import com.beardtrust.webapp.userservice.repos.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nathanael <Nathanael.Grier at your.org>
 */
@Service
public class UserInfoService {
    
    private UserRepository repo;
    
    public UserInfoService(UserRepository repo) {
        this.repo = repo;
    }
    
    public String updateService(UserEntity u, String id) {
        Optional<UserEntity> ouser = repo.findById(id);
        if (ouser.isPresent()) {
            u.setUserId(ouser.get().getUserId());
            u.setRole(ouser.get().getRole());
            repo.save(u);
            return "Update complete!";
        } else {
            return "Entity not found!";
        }
        
    }
    //Admin Access required

    public List<UserEntity> getAllUserInfos() {
        return repo.findAll();
    }
    
    public Optional<UserEntity> getSpecificUserInfos(String account_id) {
        return repo.findById(account_id);
    }
    
}
