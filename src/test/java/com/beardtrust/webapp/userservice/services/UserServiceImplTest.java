package com.beardtrust.webapp.userservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.beardtrust.webapp.userservice.dtos.UserDTO;
import com.beardtrust.webapp.userservice.entities.UserEntity;
import com.beardtrust.webapp.userservice.exceptions.DuplicateEntryException;
import com.beardtrust.webapp.userservice.models.UserRegistration;
import com.beardtrust.webapp.userservice.repos.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {
	@MockBean
	private PasswordEncoder passwordEncoder;

	@MockBean
	private UserRepository userRepository;

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Test
	public void testConstructor() {
		UserRepository userRepository = mock(UserRepository.class);
		UserServiceImpl actualUserServiceImpl = new UserServiceImpl(userRepository, new Argon2PasswordEncoder());

		assertTrue(actualUserServiceImpl.getAll().isEmpty());
		assertTrue(actualUserServiceImpl.getAllUserInfos().isEmpty());
	}

	@Test
	public void testRegisterUser() {
		UserEntity userEntity = new UserEntity();
		userEntity.setLastName("Doe");
		userEntity.setPassword("iloveyou");
		userEntity.setEmail("jane.doe@example.org");
		userEntity.setRole("Role");
		userEntity.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userEntity.setId("42");
		userEntity.setUsername("janedoe");
		userEntity.setPhone("4105551212");
		userEntity.setFirstName("Jane");
		when(this.userRepository.findByEmail(anyString())).thenReturn(userEntity);

		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setLastName("Doe");
		userRegistration.setPassword("iloveyou");
		userRegistration.setEmail("jane.doe@example.org");
		userRegistration.setRole("Role");
		userRegistration.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration.setId("42");
		userRegistration.setUsername("janedoe");
		userRegistration.setPhone("4105551212");
		userRegistration.setFirstName("Jane");
		assertThrows(DuplicateEntryException.class, () -> this.userServiceImpl.registerUser(userRegistration));
		verify(this.userRepository).findByEmail(anyString());
	}

	@Test
	public void testRegisterUser2() {
		UserEntity userEntity = new UserEntity();
		userEntity.setLastName("Doe");
		userEntity.setPassword("iloveyou");
		userEntity.setEmail(null);
		userEntity.setRole("Role");
		userEntity.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userEntity.setId("42");
		userEntity.setUsername("janedoe");
		userEntity.setPhone("4105551212");
		userEntity.setFirstName("Jane");
		when(this.userRepository.findByEmail(anyString())).thenReturn(userEntity);

		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setLastName("Doe");
		userRegistration.setPassword("iloveyou");
		userRegistration.setEmail("jane.doe@example.org");
		userRegistration.setRole("Role");
		userRegistration.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration.setId("42");
		userRegistration.setUsername("janedoe");
		userRegistration.setPhone("4105551212");
		userRegistration.setFirstName("Jane");
		assertThrows(DuplicateEntryException.class, () -> this.userServiceImpl.registerUser(userRegistration));
		verify(this.userRepository).findByEmail(anyString());
	}

	@Test
	public void testRegisterUser3() {
		UserEntity userEntity = new UserEntity();
		userEntity.setLastName("Doe");
		userEntity.setPassword("iloveyou");
		userEntity.setEmail("jane.doe@example.org");
		userEntity.setRole("Role");
		userEntity.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userEntity.setId("42");
		userEntity.setUsername("jane.doe@example.org");
		userEntity.setPhone("4105551212");
		userEntity.setFirstName("Jane");
		when(this.userRepository.findByEmail(anyString())).thenReturn(userEntity);

		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setLastName("Doe");
		userRegistration.setPassword("iloveyou");
		userRegistration.setEmail("jane.doe@example.org");
		userRegistration.setRole("Role");
		userRegistration.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration.setId("42");
		userRegistration.setUsername("janedoe");
		userRegistration.setPhone("4105551212");
		userRegistration.setFirstName("Jane");
		assertThrows(DuplicateEntryException.class, () -> this.userServiceImpl.registerUser(userRegistration));
		verify(this.userRepository).findByEmail(anyString());
	}

	@Test
	public void testDisplayUser() {
		UserEntity userEntity = new UserEntity();
		userEntity.setLastName("Doe");
		userEntity.setPassword("iloveyou");
		userEntity.setEmail("jane.doe@example.org");
		userEntity.setRole("Role");
		userEntity.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userEntity.setId("42");
		userEntity.setUsername("janedoe");
		userEntity.setPhone("4105551212");
		userEntity.setFirstName("Jane");
		Optional<UserEntity> ofResult = Optional.<UserEntity>of(userEntity);
		when(this.userRepository.findById(anyString())).thenReturn(ofResult);
		UserDTO actualDisplayUserResult = this.userServiceImpl.displayUser("42");
		assertEquals("1970-01-02", actualDisplayUserResult.getDateOfBirth().toString());
		assertEquals("janedoe", actualDisplayUserResult.getUsername());
		assertEquals("42", actualDisplayUserResult.getId());
		assertEquals("Role", actualDisplayUserResult.getRole());
		assertEquals("4105551212", actualDisplayUserResult.getPhone());
		assertEquals("Doe", actualDisplayUserResult.getLastName());
		assertEquals("Jane", actualDisplayUserResult.getFirstName());
		assertEquals("jane.doe@example.org", actualDisplayUserResult.getEmail());
		verify(this.userRepository).findById(anyString());
		assertTrue(this.userServiceImpl.getAll().isEmpty());
	}

	@Test
	public void testDisplayUser2() {
		UserEntity userEntity = new UserEntity();
		userEntity.setLastName("Last Name");
		userEntity.setPassword("iloveyou");
		userEntity.setEmail("jane.doe@example.org");
		userEntity.setRole("Role");
		userEntity.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userEntity.setId("42");
		userEntity.setUsername("janedoe");
		userEntity.setPhone("4105551212");
		userEntity.setFirstName("Jane");
		Optional<UserEntity> ofResult = Optional.<UserEntity>of(userEntity);
		when(this.userRepository.findById(anyString())).thenReturn(ofResult);
		UserDTO actualDisplayUserResult = this.userServiceImpl.displayUser("42");
		assertEquals("1970-01-02", actualDisplayUserResult.getDateOfBirth().toString());
		assertEquals("janedoe", actualDisplayUserResult.getUsername());
		assertEquals("42", actualDisplayUserResult.getId());
		assertEquals("Role", actualDisplayUserResult.getRole());
		assertEquals("4105551212", actualDisplayUserResult.getPhone());
		assertEquals("Last Name", actualDisplayUserResult.getLastName());
		assertEquals("Jane", actualDisplayUserResult.getFirstName());
		assertEquals("jane.doe@example.org", actualDisplayUserResult.getEmail());
		verify(this.userRepository).findById(anyString());
		assertTrue(this.userServiceImpl.getAll().isEmpty());
	}

	@Test
	public void testDisplayUser3() {
		UserEntity userEntity = new UserEntity();
		userEntity.setLastName("com.beardtrust.webapp.userservice.entities.UserEntity");
		userEntity.setPassword("iloveyou");
		userEntity.setEmail("jane.doe@example.org");
		userEntity.setRole("Role");
		userEntity.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userEntity.setId("42");
		userEntity.setUsername("janedoe");
		userEntity.setPhone("4105551212");
		userEntity.setFirstName("Jane");
		Optional<UserEntity> ofResult = Optional.<UserEntity>of(userEntity);
		when(this.userRepository.findById(anyString())).thenReturn(ofResult);
		UserDTO actualDisplayUserResult = this.userServiceImpl.displayUser("42");
		assertEquals("1970-01-02", actualDisplayUserResult.getDateOfBirth().toString());
		assertEquals("janedoe", actualDisplayUserResult.getUsername());
		assertEquals("42", actualDisplayUserResult.getId());
		assertEquals("Role", actualDisplayUserResult.getRole());
		assertEquals("4105551212", actualDisplayUserResult.getPhone());
		assertEquals("com.beardtrust.webapp.userservice.entities.UserEntity", actualDisplayUserResult.getLastName());
		assertEquals("Jane", actualDisplayUserResult.getFirstName());
		assertEquals("jane.doe@example.org", actualDisplayUserResult.getEmail());
		verify(this.userRepository).findById(anyString());
		assertTrue(this.userServiceImpl.getAll().isEmpty());
	}

	@Test
	public void testDisplayUser4() {
		UserEntity userEntity = new UserEntity();
		userEntity.setLastName("42");
		userEntity.setPassword("iloveyou");
		userEntity.setEmail("jane.doe@example.org");
		userEntity.setRole("Role");
		userEntity.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userEntity.setId("42");
		userEntity.setUsername("janedoe");
		userEntity.setPhone("4105551212");
		userEntity.setFirstName("Jane");
		Optional<UserEntity> ofResult = Optional.<UserEntity>of(userEntity);
		when(this.userRepository.findById(anyString())).thenReturn(ofResult);
		UserDTO actualDisplayUserResult = this.userServiceImpl.displayUser("42");
		assertEquals("1970-01-02", actualDisplayUserResult.getDateOfBirth().toString());
		assertEquals("janedoe", actualDisplayUserResult.getUsername());
		assertEquals("42", actualDisplayUserResult.getId());
		assertEquals("Role", actualDisplayUserResult.getRole());
		assertEquals("4105551212", actualDisplayUserResult.getPhone());
		assertEquals("42", actualDisplayUserResult.getLastName());
		assertEquals("Jane", actualDisplayUserResult.getFirstName());
		assertEquals("jane.doe@example.org", actualDisplayUserResult.getEmail());
		verify(this.userRepository).findById(anyString());
		assertTrue(this.userServiceImpl.getAll().isEmpty());
	}

	@Test
	public void testDisplayUser5() {
		UserEntity userEntity = new UserEntity();
		userEntity.setLastName("Doe");
		userEntity.setPassword("iloveyou");
		userEntity.setEmail("jane.doe@example.org");
		userEntity.setRole("Role");
		userEntity.setDateOfBirth(null);
		userEntity.setId("42");
		userEntity.setUsername("janedoe");
		userEntity.setPhone("4105551212");
		userEntity.setFirstName("Jane");
		Optional<UserEntity> ofResult = Optional.<UserEntity>of(userEntity);
		when(this.userRepository.findById(anyString())).thenReturn(ofResult);
		UserDTO actualDisplayUserResult = this.userServiceImpl.displayUser("42");
		assertNull(actualDisplayUserResult.getDateOfBirth());
		assertEquals("janedoe", actualDisplayUserResult.getUsername());
		assertEquals("42", actualDisplayUserResult.getId());
		assertEquals("Role", actualDisplayUserResult.getRole());
		assertEquals("4105551212", actualDisplayUserResult.getPhone());
		assertEquals("Doe", actualDisplayUserResult.getLastName());
		assertEquals("Jane", actualDisplayUserResult.getFirstName());
		assertEquals("jane.doe@example.org", actualDisplayUserResult.getEmail());
		verify(this.userRepository).findById(anyString());
		assertTrue(this.userServiceImpl.getAll().isEmpty());
	}

	@Test
	public void testDisplayUser6() {
		when(this.userRepository.findById(anyString())).thenReturn(Optional.<UserEntity>empty());
		assertNull(this.userServiceImpl.displayUser("42"));
		verify(this.userRepository).findById(anyString());
		assertTrue(this.userServiceImpl.getAll().isEmpty());
	}

	@Test
	public void testGetUserDetailsByEmail() {
		UserEntity userEntity = new UserEntity();
		userEntity.setLastName("Doe");
		userEntity.setPassword("iloveyou");
		userEntity.setEmail("jane.doe@example.org");
		userEntity.setRole("Role");
		userEntity.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userEntity.setId("42");
		userEntity.setUsername("janedoe");
		userEntity.setPhone("4105551212");
		userEntity.setFirstName("Jane");
		when(this.userRepository.findByEmail(anyString())).thenReturn(userEntity);
		UserDTO actualUserDetailsByEmail = this.userServiceImpl.getUserDetailsByEmail("janedoe");
		assertEquals("1970-01-02", actualUserDetailsByEmail.getDateOfBirth().toString());
		assertEquals("janedoe", actualUserDetailsByEmail.getUsername());
		assertEquals("42", actualUserDetailsByEmail.getId());
		assertEquals("Role", actualUserDetailsByEmail.getRole());
		assertEquals("4105551212", actualUserDetailsByEmail.getPhone());
		assertEquals("Doe", actualUserDetailsByEmail.getLastName());
		assertEquals("Jane", actualUserDetailsByEmail.getFirstName());
		assertEquals("jane.doe@example.org", actualUserDetailsByEmail.getEmail());
		verify(this.userRepository).findByEmail(anyString());
		assertTrue(this.userServiceImpl.getAll().isEmpty());
	}

	@Test
	public void testGetUserDetailsByEmail2() {
		UserEntity userEntity = new UserEntity();
		userEntity.setLastName(null);
		userEntity.setPassword("iloveyou");
		userEntity.setEmail("jane.doe@example.org");
		userEntity.setRole("Role");
		userEntity.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userEntity.setId("42");
		userEntity.setUsername("janedoe");
		userEntity.setPhone("4105551212");
		userEntity.setFirstName("Jane");
		when(this.userRepository.findByEmail(anyString())).thenReturn(userEntity);
		UserDTO actualUserDetailsByEmail = this.userServiceImpl.getUserDetailsByEmail("janedoe");
		assertEquals("1970-01-02", actualUserDetailsByEmail.getDateOfBirth().toString());
		assertEquals("janedoe", actualUserDetailsByEmail.getUsername());
		assertEquals("42", actualUserDetailsByEmail.getId());
		assertEquals("Role", actualUserDetailsByEmail.getRole());
		assertEquals("4105551212", actualUserDetailsByEmail.getPhone());
		assertNull(actualUserDetailsByEmail.getLastName());
		assertEquals("Jane", actualUserDetailsByEmail.getFirstName());
		assertEquals("jane.doe@example.org", actualUserDetailsByEmail.getEmail());
		verify(this.userRepository).findByEmail(anyString());
		assertTrue(this.userServiceImpl.getAll().isEmpty());
	}

	@Test
	public void testGetUserDetailsByEmail3() {
		UserEntity userEntity = new UserEntity();
		userEntity.setLastName("42");
		userEntity.setPassword("iloveyou");
		userEntity.setEmail("jane.doe@example.org");
		userEntity.setRole("Role");
		userEntity.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userEntity.setId("42");
		userEntity.setUsername("janedoe");
		userEntity.setPhone("4105551212");
		userEntity.setFirstName("Jane");
		when(this.userRepository.findByEmail(anyString())).thenReturn(userEntity);
		UserDTO actualUserDetailsByEmail = this.userServiceImpl.getUserDetailsByEmail("janedoe");
		assertEquals("1970-01-02", actualUserDetailsByEmail.getDateOfBirth().toString());
		assertEquals("janedoe", actualUserDetailsByEmail.getUsername());
		assertEquals("42", actualUserDetailsByEmail.getId());
		assertEquals("Role", actualUserDetailsByEmail.getRole());
		assertEquals("4105551212", actualUserDetailsByEmail.getPhone());
		assertEquals("42", actualUserDetailsByEmail.getLastName());
		assertEquals("Jane", actualUserDetailsByEmail.getFirstName());
		assertEquals("jane.doe@example.org", actualUserDetailsByEmail.getEmail());
		verify(this.userRepository).findByEmail(anyString());
		assertTrue(this.userServiceImpl.getAll().isEmpty());
	}

	@Test
	public void testLoadUserByUsername() throws UsernameNotFoundException {
		UserEntity userEntity = new UserEntity();
		userEntity.setLastName("Doe");
		userEntity.setPassword("iloveyou");
		userEntity.setEmail("jane.doe@example.org");
		userEntity.setRole("Role");
		userEntity.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userEntity.setId("42");
		userEntity.setUsername("janedoe");
		userEntity.setPhone("4105551212");
		userEntity.setFirstName("Jane");
		when(this.userRepository.findByEmail(anyString())).thenReturn(userEntity);
		UserDetails actualLoadUserByUsernameResult = this.userServiceImpl.loadUserByUsername("foo");
		assertTrue(actualLoadUserByUsernameResult.getAuthorities().isEmpty());
		assertTrue(actualLoadUserByUsernameResult.isEnabled());
		assertTrue(actualLoadUserByUsernameResult.isCredentialsNonExpired());
		assertTrue(actualLoadUserByUsernameResult.isAccountNonLocked());
		assertTrue(actualLoadUserByUsernameResult.isAccountNonExpired());
		assertEquals("jane.doe@example.org", actualLoadUserByUsernameResult.getUsername());
		assertEquals("iloveyou", actualLoadUserByUsernameResult.getPassword());
		verify(this.userRepository).findByEmail(anyString());
		assertTrue(this.userServiceImpl.getAll().isEmpty());
	}

	@Test
	public void testGetAll() {
		ArrayList<UserEntity> userEntityList = new ArrayList<UserEntity>();
		when(this.userRepository.findAll()).thenReturn(userEntityList);
		List<UserEntity> actualAll = this.userServiceImpl.getAll();
		assertSame(userEntityList, actualAll);
		assertTrue(actualAll.isEmpty());
		verify(this.userRepository).findAll();
		assertSame(actualAll, this.userServiceImpl.getAllUserInfos());
	}

	@Test
	public void testGetById() {
		UserEntity userEntity = new UserEntity();
		userEntity.setLastName("Doe");
		userEntity.setPassword("iloveyou");
		userEntity.setEmail("jane.doe@example.org");
		userEntity.setRole("Role");
		userEntity.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userEntity.setId("42");
		userEntity.setUsername("janedoe");
		userEntity.setPhone("4105551212");
		userEntity.setFirstName("Jane");
		Optional<UserEntity> ofResult = Optional.<UserEntity>of(userEntity);
		when(this.userRepository.findById(anyString())).thenReturn(ofResult);
		assertSame(userEntity, this.userServiceImpl.getById("42"));
		verify(this.userRepository).findById(anyString());
		assertTrue(this.userServiceImpl.getAll().isEmpty());
	}

	@Test
	public void testGetById2() {
		when(this.userRepository.findById(anyString())).thenReturn(Optional.<UserEntity>empty());
		assertThrows(RuntimeException.class, () -> this.userServiceImpl.getById("42"));
		verify(this.userRepository).findById(anyString());
	}

	@Test
	public void testDeleteById() {
		UserEntity userEntity = new UserEntity();
		userEntity.setLastName("Doe");
		userEntity.setPassword("iloveyou");
		userEntity.setEmail("jane.doe@example.org");
		userEntity.setRole("Role");
		userEntity.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userEntity.setId("42");
		userEntity.setUsername("janedoe");
		userEntity.setPhone("4105551212");
		userEntity.setFirstName("Jane");
		Optional<UserEntity> ofResult = Optional.<UserEntity>of(userEntity);
		doNothing().when(this.userRepository).deleteById(anyString());
		when(this.userRepository.findById(anyString())).thenReturn(ofResult);
		this.userServiceImpl.deleteById("42");
		verify(this.userRepository).deleteById(anyString());
		verify(this.userRepository).findById(anyString());
		assertTrue(this.userServiceImpl.getAll().isEmpty());
	}

	@Test
	public void testDeleteById2() {
		doNothing().when(this.userRepository).deleteById(anyString());
		when(this.userRepository.findById(anyString())).thenReturn(Optional.<UserEntity>empty());
		assertThrows(RuntimeException.class, () -> this.userServiceImpl.deleteById("42"));
		verify(this.userRepository).findById(anyString());
	}

	@Test
	public void testGetAllUserInfos() {
		ArrayList<UserEntity> userEntityList = new ArrayList<UserEntity>();
		when(this.userRepository.findAll()).thenReturn(userEntityList);
		List<UserEntity> actualAllUserInfos = this.userServiceImpl.getAllUserInfos();
		assertSame(userEntityList, actualAllUserInfos);
		assertTrue(actualAllUserInfos.isEmpty());
		verify(this.userRepository).findAll();
		assertSame(actualAllUserInfos, this.userServiceImpl.getAll());
	}

	@Test
	public void testGetSpecificUserInfos() {
		UserEntity userEntity = new UserEntity();
		userEntity.setLastName("Doe");
		userEntity.setPassword("iloveyou");
		userEntity.setEmail("jane.doe@example.org");
		userEntity.setRole("Role");
		userEntity.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userEntity.setId("42");
		userEntity.setUsername("janedoe");
		userEntity.setPhone("4105551212");
		userEntity.setFirstName("Jane");
		Optional<UserEntity> ofResult = Optional.<UserEntity>of(userEntity);
		when(this.userRepository.findById(anyString())).thenReturn(ofResult);
		Optional<UserEntity> actualSpecificUserInfos = this.userServiceImpl.getSpecificUserInfos("Account id");
		assertSame(ofResult, actualSpecificUserInfos);
		assertTrue(actualSpecificUserInfos.isPresent());
		verify(this.userRepository).findById(anyString());
		assertTrue(this.userServiceImpl.getAll().isEmpty());
	}

	@Test
	public void testFindPaginated() {
		PageImpl<UserEntity> pageImpl = new PageImpl<UserEntity>(new ArrayList<UserEntity>());
		when(this.userRepository
				.findAllByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCaseOrUsernameContainsIgnoreCaseOrEmailContainsIgnoreCaseOrPhoneContainsIgnoreCase(
						anyString(), anyString(), anyString(), anyString(), anyString(),
						(org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);
		Page<UserEntity> actualFindPaginatedResult = this.userServiceImpl.findPaginated(null, "Search");
		assertSame(pageImpl, actualFindPaginatedResult);
		assertTrue(actualFindPaginatedResult.toList().isEmpty());
		verify(this.userRepository)
				.findAllByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCaseOrUsernameContainsIgnoreCaseOrEmailContainsIgnoreCaseOrPhoneContainsIgnoreCase(
						anyString(), anyString(), anyString(), anyString(), anyString(),
						(org.springframework.data.domain.Pageable) any());
		assertTrue(this.userServiceImpl.getAll().isEmpty());
	}

	@Test
	public void testFindPaginated2() {
		UserEntity userEntity = new UserEntity();
		userEntity.setLastName("Doe");
		userEntity.setPassword("iloveyou");
		userEntity.setEmail("jane.doe@example.org");
		userEntity.setRole("Role");
		userEntity.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userEntity.setId("42");
		userEntity.setUsername("janedoe");
		userEntity.setPhone("4105551212");
		userEntity.setFirstName("Jane");

		ArrayList<UserEntity> userEntityList = new ArrayList<UserEntity>();
		userEntityList.add(userEntity);
		PageImpl<UserEntity> pageImpl = new PageImpl<UserEntity>(userEntityList);
		when(this.userRepository
				.findAllByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCaseOrUsernameContainsIgnoreCaseOrEmailContainsIgnoreCaseOrPhoneContainsIgnoreCase(
						anyString(), anyString(), anyString(), anyString(), anyString(),
						(org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);
		Page<UserEntity> actualFindPaginatedResult = this.userServiceImpl.findPaginated(null, "Search");
		assertSame(pageImpl, actualFindPaginatedResult);
		assertEquals(1, actualFindPaginatedResult.toList().size());
		verify(this.userRepository)
				.findAllByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCaseOrUsernameContainsIgnoreCaseOrEmailContainsIgnoreCaseOrPhoneContainsIgnoreCase(
						anyString(), anyString(), anyString(), anyString(), anyString(),
						(org.springframework.data.domain.Pageable) any());
		assertTrue(this.userServiceImpl.getAll().isEmpty());
	}

	@Test
	public void testFindPaginated3() {
		PageImpl<UserEntity> pageImpl = new PageImpl<UserEntity>(new ArrayList<UserEntity>());
		when(this.userRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);
		when(this.userRepository
				.findAllByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCaseOrUsernameContainsIgnoreCaseOrEmailContainsIgnoreCaseOrPhoneContainsIgnoreCase(
						anyString(), anyString(), anyString(), anyString(), anyString(),
						(org.springframework.data.domain.Pageable) any()))
				.thenReturn(new PageImpl<UserEntity>(new ArrayList<UserEntity>()));
		Page<UserEntity> actualFindPaginatedResult = this.userServiceImpl.findPaginated(null, null);
		assertSame(pageImpl, actualFindPaginatedResult);
		assertTrue(actualFindPaginatedResult.toList().isEmpty());
		verify(this.userRepository).findAll((org.springframework.data.domain.Pageable) any());
		assertTrue(this.userServiceImpl.getAll().isEmpty());
	}
}

