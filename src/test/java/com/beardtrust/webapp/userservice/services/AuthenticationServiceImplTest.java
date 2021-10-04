package com.beardtrust.webapp.userservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.beardtrust.webapp.userservice.dtos.UserDTO;
import com.beardtrust.webapp.userservice.entities.UserEntity;
import com.beardtrust.webapp.userservice.repos.UserRepository;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthenticationServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class AuthenticationServiceImplTest {
	@Autowired
	private AuthenticationServiceImpl authenticationServiceImpl;

	@MockBean
	private Environment environment;

	@MockBean
	private UserRepository userRepository;

	@Test
	public void testGetUserDetailsByEmail() {
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
		when(this.userRepository.findByEmail(anyString())).thenReturn(userEntity);
		UserDTO actualUserDetailsByEmail = this.authenticationServiceImpl.getUserDetailsByEmail("jane.doe@example.org");
		assertEquals("UserDTO(id=42, username=janedoe, email=jane.doe@example.org, phone=4105551212, firstName=Jane,"
				+ " lastName=Doe, dateOfBirth=1970-01-02, role=Role)", actualUserDetailsByEmail.toString());
		assertEquals("jane.doe@example.org", actualUserDetailsByEmail.getEmail());
		assertEquals("Doe", actualUserDetailsByEmail.getLastName());
		assertEquals("janedoe", actualUserDetailsByEmail.getUsername());
		assertEquals("4105551212", actualUserDetailsByEmail.getPhone());
		assertEquals("Jane", actualUserDetailsByEmail.getFirstName());
		assertEquals("Role", actualUserDetailsByEmail.getRole());
		assertEquals("42", actualUserDetailsByEmail.getId());
		verify(this.userRepository).findByEmail(anyString());
	}

	@Test
	public void testLoadUserByUsername() throws UsernameNotFoundException {
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
		when(this.userRepository.findByEmail(anyString())).thenReturn(userEntity);
		UserDetails actualLoadUserByUsernameResult = this.authenticationServiceImpl.loadUserByUsername("foo");
		assertEquals(1, actualLoadUserByUsernameResult.getAuthorities().size());
		assertEquals(
				"org.springframework.security.core.userdetails.User [Username=jane.doe@example.org, Password=[PROTECTED],"
						+ " Enabled=true, AccountNonExpired=true, credentialsNonExpired=true, AccountNonLocked=true, Granted"
						+ " Authorities=[Role]]",
				actualLoadUserByUsernameResult.toString());
		assertTrue(actualLoadUserByUsernameResult.isEnabled());
		assertTrue(actualLoadUserByUsernameResult.isCredentialsNonExpired());
		assertTrue(actualLoadUserByUsernameResult.isAccountNonLocked());
		assertTrue(actualLoadUserByUsernameResult.isAccountNonExpired());
		assertEquals("jane.doe@example.org", actualLoadUserByUsernameResult.getUsername());
		assertEquals("iloveyou", actualLoadUserByUsernameResult.getPassword());
		verify(this.userRepository).findByEmail(anyString());
	}

	@Test
	public void testLoadUserByUsername2() throws UsernameNotFoundException {
		UserEntity userEntity = new UserEntity();
		userEntity.setLastName("Doe");
		userEntity.setPassword("null");
		userEntity.setEmail("jane.doe@example.org");
		userEntity.setRole("admin");
		userEntity.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userEntity.setUserId("42");
		userEntity.setUsername("janedoe");
		userEntity.setPhone("4105551212");
		userEntity.setFirstName("Jane");
		when(this.userRepository.findByEmail(anyString())).thenReturn(userEntity);
		this.authenticationServiceImpl.loadUserByUsername("foo");
		verify(this.userRepository).findByEmail(anyString());
	}

	@Test
	public void testLoadUserByUsername3() throws UsernameNotFoundException {
		UserEntity userEntity = new UserEntity();
		userEntity.setLastName("Doe");
		userEntity.setPassword("iloveyou");
		userEntity.setEmail("jane.doe@example.org");
		userEntity.setRole("user");
		userEntity.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userEntity.setUserId("42");
		userEntity.setUsername("janedoe");
		userEntity.setPhone("4105551212");
		userEntity.setFirstName("Jane");
		when(this.userRepository.findByEmail(anyString())).thenReturn(userEntity);
		this.authenticationServiceImpl.loadUserByUsername("foo");
		verify(this.userRepository).findByEmail(anyString());
	}
}

