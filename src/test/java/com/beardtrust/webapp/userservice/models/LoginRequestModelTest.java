package com.beardtrust.webapp.userservice.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LoginRequestModelTest {
	@Test
	public void testCanEqual() {
		assertFalse((new LoginRequestModel()).canEqual("Other"));
	}

	@Test
	public void testCanEqual2() {
		LoginRequestModel loginRequestModel = new LoginRequestModel();

		LoginRequestModel loginRequestModel1 = new LoginRequestModel();
		loginRequestModel1.setEmail("jane.doe@example.org");
		loginRequestModel1.setPassword("iloveyou");
		assertTrue(loginRequestModel.canEqual(loginRequestModel1));
	}

	@Test
	public void testConstructor() {
		LoginRequestModel actualLoginRequestModel = new LoginRequestModel();
		actualLoginRequestModel.setEmail("jane.doe@example.org");
		actualLoginRequestModel.setPassword("iloveyou");
		assertEquals("jane.doe@example.org", actualLoginRequestModel.getEmail());
		assertEquals("iloveyou", actualLoginRequestModel.getPassword());
	}

	@Test
	public void testEquals() {
		LoginRequestModel loginRequestModel = new LoginRequestModel();
		loginRequestModel.setEmail("jane.doe@example.org");
		loginRequestModel.setPassword("iloveyou");
		assertFalse(loginRequestModel.equals(null));
	}

	@Test
	public void testEquals10() {
		LoginRequestModel loginRequestModel = new LoginRequestModel();
		loginRequestModel.setEmail("jane.doe@example.org");
		loginRequestModel.setPassword(null);

		LoginRequestModel loginRequestModel1 = new LoginRequestModel();
		loginRequestModel1.setEmail("jane.doe@example.org");
		loginRequestModel1.setPassword(null);
		assertTrue(loginRequestModel.equals(loginRequestModel1));
		int expectedHashCodeResult = loginRequestModel.hashCode();
		assertEquals(expectedHashCodeResult, loginRequestModel1.hashCode());
	}

	@Test
	public void testEquals2() {
		LoginRequestModel loginRequestModel = new LoginRequestModel();
		loginRequestModel.setEmail("jane.doe@example.org");
		loginRequestModel.setPassword("iloveyou");
		assertFalse(loginRequestModel.equals("Different type to LoginRequestModel"));
	}

	@Test
	public void testEquals3() {
		LoginRequestModel loginRequestModel = new LoginRequestModel();
		loginRequestModel.setEmail("jane.doe@example.org");
		loginRequestModel.setPassword("iloveyou");
		assertTrue(loginRequestModel.equals(loginRequestModel));
		int expectedHashCodeResult = loginRequestModel.hashCode();
		assertEquals(expectedHashCodeResult, loginRequestModel.hashCode());
	}

	@Test
	public void testEquals4() {
		LoginRequestModel loginRequestModel = new LoginRequestModel();
		loginRequestModel.setEmail("jane.doe@example.org");
		loginRequestModel.setPassword("iloveyou");

		LoginRequestModel loginRequestModel1 = new LoginRequestModel();
		loginRequestModel1.setEmail("jane.doe@example.org");
		loginRequestModel1.setPassword("iloveyou");
		assertTrue(loginRequestModel.equals(loginRequestModel1));
		int expectedHashCodeResult = loginRequestModel.hashCode();
		assertEquals(expectedHashCodeResult, loginRequestModel1.hashCode());
	}

	@Test
	public void testEquals5() {
		LoginRequestModel loginRequestModel = new LoginRequestModel();
		loginRequestModel.setEmail(null);
		loginRequestModel.setPassword("iloveyou");

		LoginRequestModel loginRequestModel1 = new LoginRequestModel();
		loginRequestModel1.setEmail("jane.doe@example.org");
		loginRequestModel1.setPassword("iloveyou");
		assertFalse(loginRequestModel.equals(loginRequestModel1));
	}

	@Test
	public void testEquals6() {
		LoginRequestModel loginRequestModel = new LoginRequestModel();
		loginRequestModel.setEmail("iloveyou");
		loginRequestModel.setPassword("iloveyou");

		LoginRequestModel loginRequestModel1 = new LoginRequestModel();
		loginRequestModel1.setEmail("jane.doe@example.org");
		loginRequestModel1.setPassword("iloveyou");
		assertFalse(loginRequestModel.equals(loginRequestModel1));
	}

	@Test
	public void testEquals7() {
		LoginRequestModel loginRequestModel = new LoginRequestModel();
		loginRequestModel.setEmail("jane.doe@example.org");
		loginRequestModel.setPassword("jane.doe@example.org");

		LoginRequestModel loginRequestModel1 = new LoginRequestModel();
		loginRequestModel1.setEmail("jane.doe@example.org");
		loginRequestModel1.setPassword("iloveyou");
		assertFalse(loginRequestModel.equals(loginRequestModel1));
	}

	@Test
	public void testEquals8() {
		LoginRequestModel loginRequestModel = new LoginRequestModel();
		loginRequestModel.setEmail("jane.doe@example.org");
		loginRequestModel.setPassword(null);

		LoginRequestModel loginRequestModel1 = new LoginRequestModel();
		loginRequestModel1.setEmail("jane.doe@example.org");
		loginRequestModel1.setPassword("iloveyou");
		assertFalse(loginRequestModel.equals(loginRequestModel1));
	}

	@Test
	public void testEquals9() {
		LoginRequestModel loginRequestModel = new LoginRequestModel();
		loginRequestModel.setEmail(null);
		loginRequestModel.setPassword("iloveyou");

		LoginRequestModel loginRequestModel1 = new LoginRequestModel();
		loginRequestModel1.setEmail(null);
		loginRequestModel1.setPassword("iloveyou");
		assertTrue(loginRequestModel.equals(loginRequestModel1));
		int expectedHashCodeResult = loginRequestModel.hashCode();
		assertEquals(expectedHashCodeResult, loginRequestModel1.hashCode());
	}
}

