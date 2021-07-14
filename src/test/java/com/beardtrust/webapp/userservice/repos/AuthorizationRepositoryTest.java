package com.beardtrust.webapp.userservice.repos;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class AuthorizationRepositoryTest {
	@Autowired
	private AuthorizationRepository authorizationRepository;

	@Test
	public void testFindByUserId() {
		assertFalse(this.authorizationRepository.findByUserId("foo").isPresent());
	}
}

