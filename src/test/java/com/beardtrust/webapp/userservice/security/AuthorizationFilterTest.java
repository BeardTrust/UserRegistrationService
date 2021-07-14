package com.beardtrust.webapp.userservice.security;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.beardtrust.webapp.userservice.services.AuthorizationService;

import java.io.IOException;

import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import org.apache.catalina.connector.Response;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.reactive.context.StandardReactiveWebEnvironment;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.access.intercept.RunAsImplAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;

public class AuthorizationFilterTest {
	@Test
	public void testConstructor() {
		ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<AuthenticationProvider>();
		authenticationProviderList.add(new RunAsImplAuthenticationProvider());
		ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
		assertTrue((new AuthorizationFilter(authenticationManager, new StandardReactiveWebEnvironment(),
				mock(AuthorizationService.class)))
				.getEnvironment() instanceof org.springframework.web.context.support.StandardServletEnvironment);
	}

	@Test
	public void testDoFilterInternal() throws IOException, ServletException {
		ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<AuthenticationProvider>();
		authenticationProviderList.add(new RunAsImplAuthenticationProvider());
		ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
		AuthorizationFilter authorizationFilter = new AuthorizationFilter(authenticationManager,
				new StandardReactiveWebEnvironment(), mock(AuthorizationService.class));
		MockHttpServletRequest request = new MockHttpServletRequest();
		Response response = new Response();
		FilterChain filterChain = mock(FilterChain.class);
		doNothing().when(filterChain).doFilter((javax.servlet.ServletRequest) any(), (javax.servlet.ServletResponse) any());
		authorizationFilter.doFilterInternal(request, response, filterChain);
		verify(filterChain).doFilter((javax.servlet.ServletRequest) any(), (javax.servlet.ServletResponse) any());
	}
}

