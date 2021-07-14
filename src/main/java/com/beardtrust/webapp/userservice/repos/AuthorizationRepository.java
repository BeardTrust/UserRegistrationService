package com.beardtrust.webapp.userservice.repos;

import com.beardtrust.webapp.userservice.entities.UserEntity;
import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * The interface Authorization repository.
 *
 * @author Matthew Crowell <Matthew.Crowell@Smoothstack.com>
 */
@org.springframework.stereotype.Repository
public interface AuthorizationRepository extends Repository<UserEntity, String> {
	/**
	 * Find by user id optional.
	 *
	 * @param id the id
	 * @return the optional
	 */
	Optional<UserEntity> findByUserId(String id);
}
