package com.beardtrust.webapp.userservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.beardtrust.webapp.userservice.dtos.UserDTO;
import com.beardtrust.webapp.userservice.entities.UserEntity;
import com.beardtrust.webapp.userservice.models.UserRegistration;
import com.beardtrust.webapp.userservice.repos.UserRepository;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserRegistrationServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class UserRegistrationServiceImplTest {
	@MockBean
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRegistrationServiceImpl userRegistrationServiceImpl;

	@MockBean
	private UserRepository userRepository;

	@Test
	public void testRegisterUser() {
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
		when(this.passwordEncoder.encode((CharSequence) any())).thenReturn("foo");

		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setLastName("Doe");
		userRegistration.setPassword("iloveyou");
		userRegistration.setEmail("jane.doe@example.org");
		userRegistration.setRole("Role");
		userRegistration.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration.setUserId("42");
		userRegistration.setUsername("janedoe");
		userRegistration.setPhone("4105551212");
		userRegistration.setFirstName("Jane");
		this.userRegistrationServiceImpl.registerUser(userRegistration);
		verify(this.userRepository).findByEmail(anyString());
		verify(this.passwordEncoder).encode((CharSequence) any());
		assertEquals("foo", userRegistration.getPassword());
	}

	@Test
	public void testDisplayUser() {
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
		UserDTO actualDisplayUserResult = this.userRegistrationServiceImpl.displayUser("42");
		assertEquals("UserDTO(userId=42, username=janedoe, email=jane.doe@example.org, phone=4105551212, firstName=Jane,"
				+ " lastName=Doe, dateOfBirth=1970-01-02, role=Role)", actualDisplayUserResult.toString());
		assertEquals("jane.doe@example.org", actualDisplayUserResult.getEmail());
		assertEquals("Doe", actualDisplayUserResult.getLastName());
		assertEquals("janedoe", actualDisplayUserResult.getUsername());
		assertEquals("4105551212", actualDisplayUserResult.getPhone());
		assertEquals("Jane", actualDisplayUserResult.getFirstName());
		assertEquals("Role", actualDisplayUserResult.getRole());
		assertEquals("42", actualDisplayUserResult.getUserId());
		verify(this.userRepository).findById(anyString());
	}

	@Test
	public void testDisplayUser2() {
		UserEntity userEntity = new UserEntity();
		userEntity.setLastName("Last Name");
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
		UserDTO actualDisplayUserResult = this.userRegistrationServiceImpl.displayUser("42");
		assertEquals("UserDTO(userId=42, username=janedoe, email=jane.doe@example.org, phone=4105551212, firstName=Jane,"
				+ " lastName=Last Name, dateOfBirth=1970-01-02, role=Role)", actualDisplayUserResult.toString());
		assertEquals("jane.doe@example.org", actualDisplayUserResult.getEmail());
		assertEquals("Last Name", actualDisplayUserResult.getLastName());
		assertEquals("janedoe", actualDisplayUserResult.getUsername());
		assertEquals("4105551212", actualDisplayUserResult.getPhone());
		assertEquals("Jane", actualDisplayUserResult.getFirstName());
		assertEquals("Role", actualDisplayUserResult.getRole());
		assertEquals("42", actualDisplayUserResult.getUserId());
		verify(this.userRepository).findById(anyString());
	}

	@Test
	public void testDisplayUser3() {
		UserEntity userEntity = new UserEntity();
		userEntity.setLastName("42");
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
		UserDTO actualDisplayUserResult = this.userRegistrationServiceImpl.displayUser("42");
		assertEquals("UserDTO(userId=42, username=janedoe, email=jane.doe@example.org, phone=4105551212, firstName=Jane,"
				+ " lastName=42, dateOfBirth=1970-01-02, role=Role)", actualDisplayUserResult.toString());
		assertEquals("jane.doe@example.org", actualDisplayUserResult.getEmail());
		assertEquals("42", actualDisplayUserResult.getLastName());
		assertEquals("janedoe", actualDisplayUserResult.getUsername());
		assertEquals("4105551212", actualDisplayUserResult.getPhone());
		assertEquals("Jane", actualDisplayUserResult.getFirstName());
		assertEquals("Role", actualDisplayUserResult.getRole());
		assertEquals("42", actualDisplayUserResult.getUserId());
		verify(this.userRepository).findById(anyString());
	}

	@Test
	public void testDisplayUser4() {
		UserEntity userEntity = new UserEntity();
		userEntity.setLastName("Doe");
		userEntity.setPassword("iloveyou");
		userEntity.setEmail("jane.doe@example.org");
		userEntity.setRole("Role");
		userEntity.setDateOfBirth(null);
		userEntity.setUserId("42");
		userEntity.setUsername("janedoe");
		userEntity.setPhone("4105551212");
		userEntity.setFirstName("Jane");
		Optional<UserEntity> ofResult = Optional.<UserEntity>of(userEntity);
		when(this.userRepository.findById(anyString())).thenReturn(ofResult);
		UserDTO actualDisplayUserResult = this.userRegistrationServiceImpl.displayUser("42");
		assertNull(actualDisplayUserResult.getDateOfBirth());
		assertEquals("janedoe", actualDisplayUserResult.getUsername());
		assertEquals("42", actualDisplayUserResult.getUserId());
		assertEquals("Role", actualDisplayUserResult.getRole());
		assertEquals("4105551212", actualDisplayUserResult.getPhone());
		assertEquals("Doe", actualDisplayUserResult.getLastName());
		assertEquals("Jane", actualDisplayUserResult.getFirstName());
		assertEquals("jane.doe@example.org", actualDisplayUserResult.getEmail());
		verify(this.userRepository).findById(anyString());
	}

	@Test
	public void testDisplayUser5() {
		when(this.userRepository.findById(anyString())).thenReturn(Optional.<UserEntity>empty());
		assertNull(this.userRegistrationServiceImpl.displayUser("42"));
		verify(this.userRepository).findById(anyString());
	}

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
		UserDTO actualUserDetailsByEmail = this.userRegistrationServiceImpl.getUserDetailsByEmail("janedoe");
		assertEquals("UserDTO(userId=42, username=janedoe, email=jane.doe@example.org, phone=4105551212, firstName=Jane,"
				+ " lastName=Doe, dateOfBirth=1970-01-02, role=Role)", actualUserDetailsByEmail.toString());
		assertEquals("jane.doe@example.org", actualUserDetailsByEmail.getEmail());
		assertEquals("Doe", actualUserDetailsByEmail.getLastName());
		assertEquals("janedoe", actualUserDetailsByEmail.getUsername());
		assertEquals("4105551212", actualUserDetailsByEmail.getPhone());
		assertEquals("Jane", actualUserDetailsByEmail.getFirstName());
		assertEquals("Role", actualUserDetailsByEmail.getRole());
		assertEquals("42", actualUserDetailsByEmail.getUserId());
		verify(this.userRepository).findByEmail(anyString());
	}

	@Test
	public void testGetUserDetailsByEmail2() {
		UserEntity userEntity = new UserEntity();
		userEntity.setLastName(null);
		userEntity.setPassword("iloveyou");
		userEntity.setEmail("jane.doe@example.org");
		userEntity.setRole("Role");
		userEntity.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userEntity.setUserId("42");
		userEntity.setUsername("janedoe");
		userEntity.setPhone("4105551212");
		userEntity.setFirstName("Jane");
		when(this.userRepository.findByEmail(anyString())).thenReturn(userEntity);
		UserDTO actualUserDetailsByEmail = this.userRegistrationServiceImpl.getUserDetailsByEmail("janedoe");
		assertEquals("UserDTO(userId=42, username=janedoe, email=jane.doe@example.org, phone=4105551212, firstName=Jane,"
				+ " lastName=null, dateOfBirth=1970-01-02, role=Role)", actualUserDetailsByEmail.toString());
		assertEquals("jane.doe@example.org", actualUserDetailsByEmail.getEmail());
		assertNull(actualUserDetailsByEmail.getLastName());
		assertEquals("janedoe", actualUserDetailsByEmail.getUsername());
		assertEquals("4105551212", actualUserDetailsByEmail.getPhone());
		assertEquals("Jane", actualUserDetailsByEmail.getFirstName());
		assertEquals("Role", actualUserDetailsByEmail.getRole());
		assertEquals("42", actualUserDetailsByEmail.getUserId());
		verify(this.userRepository).findByEmail(anyString());
	}

	@Test
	public void testGetUserDetailsByEmail3() {
		UserEntity userEntity = new UserEntity();
		userEntity.setLastName("42");
		userEntity.setPassword("iloveyou");
		userEntity.setEmail("jane.doe@example.org");
		userEntity.setRole("Role");
		userEntity.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userEntity.setUserId("42");
		userEntity.setUsername("janedoe");
		userEntity.setPhone("4105551212");
		userEntity.setFirstName("Jane");
		when(this.userRepository.findByEmail(anyString())).thenReturn(userEntity);
		UserDTO actualUserDetailsByEmail = this.userRegistrationServiceImpl.getUserDetailsByEmail("janedoe");
		assertEquals("UserDTO(userId=42, username=janedoe, email=jane.doe@example.org, phone=4105551212, firstName=Jane,"
				+ " lastName=42, dateOfBirth=1970-01-02, role=Role)", actualUserDetailsByEmail.toString());
		assertEquals("jane.doe@example.org", actualUserDetailsByEmail.getEmail());
		assertEquals("42", actualUserDetailsByEmail.getLastName());
		assertEquals("janedoe", actualUserDetailsByEmail.getUsername());
		assertEquals("4105551212", actualUserDetailsByEmail.getPhone());
		assertEquals("Jane", actualUserDetailsByEmail.getFirstName());
		assertEquals("Role", actualUserDetailsByEmail.getRole());
		assertEquals("42", actualUserDetailsByEmail.getUserId());
		verify(this.userRepository).findByEmail(anyString());
	}

	@Test
	public void testGetUserDetailsByEmail4() {
		UserEntity userEntity = new UserEntity();
		userEntity.setLastName("42");
		userEntity.setPassword("iloveyou");
		userEntity.setEmail("jane.doe@example.org");
		userEntity.setRole("Role");
		userEntity.setDateOfBirth(null);
		userEntity.setUserId("42");
		userEntity.setUsername("janedoe");
		userEntity.setPhone("4105551212");
		userEntity.setFirstName("Jane");
		when(this.userRepository.findByEmail(anyString())).thenReturn(userEntity);
		UserDTO actualUserDetailsByEmail = this.userRegistrationServiceImpl.getUserDetailsByEmail("janedoe");
		assertNull(actualUserDetailsByEmail.getDateOfBirth());
		assertEquals("janedoe", actualUserDetailsByEmail.getUsername());
		assertEquals("42", actualUserDetailsByEmail.getUserId());
		assertEquals("Role", actualUserDetailsByEmail.getRole());
		assertEquals("4105551212", actualUserDetailsByEmail.getPhone());
		assertEquals("42", actualUserDetailsByEmail.getLastName());
		assertEquals("Jane", actualUserDetailsByEmail.getFirstName());
		assertEquals("jane.doe@example.org", actualUserDetailsByEmail.getEmail());
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
		UserDetails actualLoadUserByUsernameResult = this.userRegistrationServiceImpl.loadUserByUsername("foo");
		assertTrue(actualLoadUserByUsernameResult.getAuthorities().isEmpty());
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
		userEntity.setRole("Role");
		userEntity.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userEntity.setUserId("42");
		userEntity.setUsername("janedoe");
		userEntity.setPhone("4105551212");
		userEntity.setFirstName("Jane");
		when(this.userRepository.findByEmail(anyString())).thenReturn(userEntity);
		this.userRegistrationServiceImpl.loadUserByUsername("foo");
		verify(this.userRepository).findByEmail(anyString());
	}
}

