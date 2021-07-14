package com.beardtrust.webapp.userservice.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.beardtrust.webapp.userservice.repos.UserRepository;
import com.beardtrust.webapp.userservice.services.AuthenticationServiceImpl;

import java.util.ArrayList;

import org.apache.catalina.connector.Response;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.reactive.context.StandardReactiveWebEnvironment;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.access.intercept.RunAsImplAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.AuthenticationException;

public class AuthenticationFilterTest {
	@Test
	public void testConstructor() {
		AuthenticationServiceImpl authenticationService = new AuthenticationServiceImpl(
				new StandardReactiveWebEnvironment(), mock(UserRepository.class));

		StandardReactiveWebEnvironment environment = new StandardReactiveWebEnvironment();

		ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<AuthenticationProvider>();
		authenticationProviderList.add(new RunAsImplAuthenticationProvider());
		AuthenticationFilter actualAuthenticationFilter = new AuthenticationFilter(authenticationService, environment,
				new ProviderManager(authenticationProviderList));

		assertTrue(actualAuthenticationFilter
				.getEnvironment() instanceof org.springframework.web.context.support.StandardServletEnvironment);
		assertEquals("username", actualAuthenticationFilter.getUsernameParameter());
		assertTrue(actualAuthenticationFilter
				.getRememberMeServices() instanceof org.springframework.security.web.authentication.NullRememberMeServices);
		assertEquals("password", actualAuthenticationFilter.getPasswordParameter());
	}

	@Test
	public void testAttemptAuthentication() throws AuthenticationException {
		ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<AuthenticationProvider>();
		authenticationProviderList.add(new RunAsImplAuthenticationProvider());
		ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
		AuthenticationServiceImpl authenticationService = new AuthenticationServiceImpl(
				new StandardReactiveWebEnvironment(), mock(UserRepository.class));

		AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationService,
				new StandardReactiveWebEnvironment(), authenticationManager);
		MockHttpServletRequest request = new MockHttpServletRequest();
		assertThrows(RuntimeException.class, () -> authenticationFilter.attemptAuthentication(request, new Response()));
	}

	@Test
	public void testAttemptAuthentication2() throws AuthenticationException {
		AuthenticationServiceImpl authenticationService = new AuthenticationServiceImpl(
				new StandardReactiveWebEnvironment(), mock(UserRepository.class));

		StandardReactiveWebEnvironment environment = new StandardReactiveWebEnvironment();
		RunAsImplAuthenticationProvider runAsImplAuthenticationProvider = new RunAsImplAuthenticationProvider();
		RunAsImplAuthenticationProvider runAsImplAuthenticationProvider1 = new RunAsImplAuthenticationProvider();
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationService, environment,
				new ProviderManager(runAsImplAuthenticationProvider, runAsImplAuthenticationProvider1,
						new RunAsImplAuthenticationProvider()));
		MockHttpServletRequest request = new MockHttpServletRequest();
		assertThrows(RuntimeException.class, () -> authenticationFilter.attemptAuthentication(request, new Response()));
	}
}

