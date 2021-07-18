package com.beardtrust.webapp.userservice.controllers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import com.beardtrust.webapp.userservice.entities.UserEntity;
import com.beardtrust.webapp.userservice.repos.UserRepository;
import com.beardtrust.webapp.userservice.services.UserInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserInfoController.class})
@ExtendWith(SpringExtension.class)
public class UserInfoControllerTest {
	@Autowired
	private UserInfoController userInfoController;

	@MockBean
	private UserInfoService userInfoService;

	@MockBean
	private UserRepository userRepository;

	@Test
	public void testGetAllUserInfos() throws Exception {
		when(this.userInfoService.getAllUserInfos()).thenReturn(new ArrayList<UserEntity>());
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users");
		MockMvcBuilders.standaloneSetup(this.userInfoController)
				.build()
				.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));
	}

	@Test
	public void testGetSpecificUserInfos() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/id/{id}", "42");
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userInfoController)
				.build()
				.perform(requestBuilder);
		actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500));
	}

	@Test
	public void testUpdateUser() throws Exception {
		when(this.userInfoService.updateService((UserEntity) any(), anyString())).thenReturn("foo");

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
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users/id/{id}", "42")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		MockMvcBuilders.standaloneSetup(this.userInfoController)
				.build()
				.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
				.andExpect(MockMvcResultMatchers.content().string("foo"));
	}
}

