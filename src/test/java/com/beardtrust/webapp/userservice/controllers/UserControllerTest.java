package com.beardtrust.webapp.userservice.controllers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.beardtrust.webapp.userservice.entities.UserEntity;
import com.beardtrust.webapp.userservice.models.UserRegistration;
import com.beardtrust.webapp.userservice.repos.UserRepository;
import com.beardtrust.webapp.userservice.services.UserService;
import com.beardtrust.webapp.userservice.services.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
public class UserControllerTest {
	@Autowired
	private UserController userController;

	@MockBean
	private UserService userService;

	@Test
	public void testConstructor() {
		UserRepository userRepository = mock(UserRepository.class);
		UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, new Argon2PasswordEncoder());

		assertTrue((new UserController(userServiceImpl)).getAllUserInfos().isEmpty());
		assertTrue(userServiceImpl.getAll().isEmpty());
		assertTrue(userServiceImpl.getAllUserInfos().isEmpty());
	}

	@Test
	public void testCreateUser() {
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
		UserRepository userRepository = mock(UserRepository.class);
		when(userRepository.save((UserEntity) any())).thenReturn(userEntity);
		UserController userController = new UserController(
				new UserServiceImpl(userRepository, new Argon2PasswordEncoder()));

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
		userController.createUser(userEntity1);
		verify(userRepository).save((UserEntity) any());
		assertTrue(userController.getAllUserInfos().isEmpty());
	}

	@Test
	public void testGetAllUserInfos() throws Exception {
		when(this.userService.getAllUserInfos()).thenReturn(new ArrayList<UserEntity>());
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/");
		MockMvcBuilders.standaloneSetup(this.userController)
				.build()
				.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));
	}

	@Test
	public void testGetAllUserInfos2() throws Exception {
		when(this.userService.getAllUserInfos()).thenReturn(new ArrayList<UserEntity>());
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/");
		getResult.contentType("Not all who wander are lost");
		MockMvcBuilders.standaloneSetup(this.userController)
				.build()
				.perform(getResult)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));
	}

	@Test
	public void testGetSpecificUserInfos() throws Exception {
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
		when(this.userService.getSpecificUserInfos(anyString())).thenReturn(ofResult);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/{id}", "42");
		MockMvcBuilders.standaloneSetup(this.userController)
				.build()
				.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content()
						.string(
								"{\"userId\":\"42\",\"username\":\"janedoe\",\"password\":\"iloveyou\",\"email\":\"jane.doe@example.org\",\"phone\":"
										+ "\"4105551212\",\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"dateOfBirth\":[1970,1,2],\"role\":\"Role\"}"));
	}

	@Test
	public void testDeleteUser() throws Exception {
		doNothing().when(this.userService).deleteById(anyString());
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/admin/users/{id}", "42");
		MockMvcBuilders.standaloneSetup(this.userController)
				.build()
				.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testDeleteUser2() throws Exception {
		doNothing().when(this.userService).deleteById(anyString());
		MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/admin/users/{id}", "42");
		deleteResult.contentType("Not all who wander are lost");
		MockMvcBuilders.standaloneSetup(this.userController)
				.build()
				.perform(deleteResult)
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testDisplayUserById() throws Exception {
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
		when(this.userService.getById(anyString())).thenReturn(userEntity);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/users/{id}", "42");
		MockMvcBuilders.standaloneSetup(this.userController)
				.build()
				.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content()
						.string(
								"{\"userId\":\"42\",\"username\":\"janedoe\",\"password\":\"iloveyou\",\"email\":\"jane.doe@example.org\",\"phone\":"
										+ "\"4105551212\",\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"dateOfBirth\":[1970,1,2],\"role\":\"Role\"}"));
	}

	@Test
	public void testRegisterUser() throws Exception {
		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setLastName("Doe");
		userRegistration.setPassword("iloveyou");
		userRegistration.setEmail("jane.doe@example.org");
		userRegistration.setRole("Role");
		userRegistration.setDateOfBirth(null);
		userRegistration.setUserId("42");
		userRegistration.setUsername("janedoe");
		userRegistration.setPhone("4105551212");
		userRegistration.setFirstName("Jane");
		String content = (new ObjectMapper()).writeValueAsString(userRegistration);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
				.build()
				.perform(requestBuilder);
		actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
	}

	@Test
	public void testUpdateUser() throws Exception {
		when(this.userService.updateService((UserEntity) any(), anyString())).thenReturn("foo");

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
		String content = (new ObjectMapper()).writeValueAsString(userEntity);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users/{id}", "42")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		MockMvcBuilders.standaloneSetup(this.userController)
				.build()
				.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
				.andExpect(MockMvcResultMatchers.content().string("foo"));
	}

	@Test
	public void testUpdateUserByAdmin() throws Exception {
		doNothing().when(this.userService).save((UserEntity) any());

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
		String content = (new ObjectMapper()).writeValueAsString(userEntity);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/admin/users/{id}", "42")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		MockMvcBuilders.standaloneSetup(this.userController)
				.build()
				.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
}

