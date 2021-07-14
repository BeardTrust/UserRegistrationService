package com.beardtrust.webapp.userservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.beardtrust.webapp.userservice.entities.UserEntity;
import com.beardtrust.webapp.userservice.repos.UserRepository;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserInfoService.class})
@ExtendWith(SpringExtension.class)
public class UserInfoServiceTest {
	@Autowired
	private UserInfoService userInfoService;

	@MockBean
	private UserRepository userRepository;

	@Test
	public void testConstructor() {
		assertTrue((new UserInfoService(mock(UserRepository.class))).getAllUserInfos().isEmpty());
	}

	@Test
	public void testUpdateService() {
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
		Optional<UserEntity> ofResult = Optional.<UserEntity>of(userEntity);

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
		when(this.userRepository.save((UserEntity) any())).thenReturn(userEntity1);
		when(this.userRepository.findById(anyString())).thenReturn(ofResult);

		UserEntity userEntity2 = new UserEntity();
		userEntity2.setLastName("Doe");
		userEntity2.setPassword("iloveyou");
		userEntity2.setEmail("jane.doe@example.org");
		userEntity2.setRole("Role");
		userEntity2.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userEntity2.setUserId("42");
		userEntity2.setUsername("janedoe");
		userEntity2.setPhone("4105551212");
		userEntity2.setFirstName("Jane");
		assertEquals("Update complete!", this.userInfoService.updateService(userEntity2, "42"));
		verify(this.userRepository).findById(anyString());
		verify(this.userRepository).save((UserEntity) any());
		assertTrue(this.userInfoService.getAllUserInfos().isEmpty());
	}

	@Test
	public void testUpdateService2() {
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
		when(this.userRepository.save((UserEntity) any())).thenReturn(userEntity);
		when(this.userRepository.findById(anyString())).thenReturn(Optional.<UserEntity>empty());

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
		assertEquals("Entity not found!", this.userInfoService.updateService(userEntity1, "42"));
		verify(this.userRepository).findById(anyString());
		assertTrue(this.userInfoService.getAllUserInfos().isEmpty());
	}

	@Test
	public void testGetAllUserInfos() {
		ArrayList<UserEntity> userEntityList = new ArrayList<UserEntity>();
		when(this.userRepository.findAll()).thenReturn(userEntityList);
		List<UserEntity> actualAllUserInfos = this.userInfoService.getAllUserInfos();
		assertSame(userEntityList, actualAllUserInfos);
		assertTrue(actualAllUserInfos.isEmpty());
		verify(this.userRepository).findAll();
	}

	@Test
	public void testGetSpecificUserInfos() {
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
		Optional<UserEntity> ofResult = Optional.<UserEntity>of(userEntity);
		when(this.userRepository.findById(anyString())).thenReturn(ofResult);
		Optional<UserEntity> actualSpecificUserInfos = this.userInfoService.getSpecificUserInfos("Account id");
		assertSame(ofResult, actualSpecificUserInfos);
		assertTrue(actualSpecificUserInfos.isPresent());
		verify(this.userRepository).findById(anyString());
		assertTrue(this.userInfoService.getAllUserInfos().isEmpty());
	}
}

