package com.beardtrust.webapp.userservice.repos;

import static org.junit.jupiter.api.Assertions.assertNull;

import com.beardtrust.webapp.userservice.entities.UserEntity;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {
	@Autowired
	private UserRepository userRepository;

	@Test
	public void testFindByEmail() {
		UserEntity userEntity = new UserEntity();
		userEntity.setLastName("Doe");
		userEntity.setPassword("iloveyou");
		userEntity.setEmail("jane.doe@example.org");
		userEntity.setRole("Role");
		userEntity.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userEntity.setUserId("42");
		userEntity.setUsername("janedoe");
		userEntity.setPhone("4105551212");
		userEntity.setFirstName("Jane");

		UserEntity userEntity1 = new UserEntity();
		userEntity1.setLastName("Doe");
		userEntity1.setPassword("iloveyou");
		userEntity1.setEmail("jane.doe@example.org");
		userEntity1.setRole("Role");
		userEntity1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userEntity1.setUserId("42");
		userEntity1.setUsername("janedoe");
		userEntity1.setPhone("4105551212");
		userEntity1.setFirstName("Jane");
		this.userRepository.<UserEntity>save(userEntity);
		this.userRepository.<UserEntity>save(userEntity1);
		assertNull(this.userRepository.findByEmail("foo"));
	}
}

