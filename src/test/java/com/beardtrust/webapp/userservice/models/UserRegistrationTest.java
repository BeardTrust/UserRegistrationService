package com.beardtrust.webapp.userservice.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class UserRegistrationTest {
	@Test
	public void testCanEqual() {
		assertFalse((new UserRegistration()).canEqual("Other"));
	}

	@Test
	public void testCanEqual2() {
		UserRegistration userRegistration = new UserRegistration();

		UserRegistration userRegistration1 = new UserRegistration();
		userRegistration1.setLastName("Doe");
		userRegistration1.setPassword("iloveyou");
		userRegistration1.setEmail("jane.doe@example.org");
		userRegistration1.setRole("Role");
		userRegistration1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration1.setId("42");
		userRegistration1.setUsername("janedoe");
		userRegistration1.setPhone("4105551212");
		userRegistration1.setFirstName("Jane");
		assertTrue(userRegistration.canEqual(userRegistration1));
	}

	@Test
	public void testConstructor() {
		UserRegistration actualUserRegistration = new UserRegistration();
		LocalDate ofEpochDayResult = LocalDate.ofEpochDay(1L);
		actualUserRegistration.setDateOfBirth(ofEpochDayResult);
		actualUserRegistration.setEmail("jane.doe@example.org");
		actualUserRegistration.setFirstName("Jane");
		actualUserRegistration.setLastName("Doe");
		actualUserRegistration.setPassword("iloveyou");
		actualUserRegistration.setPhone("4105551212");
		actualUserRegistration.setRole("Role");
		actualUserRegistration.setId("42");
		actualUserRegistration.setUsername("janedoe");
		assertSame(ofEpochDayResult, actualUserRegistration.getDateOfBirth());
		assertEquals("jane.doe@example.org", actualUserRegistration.getEmail());
		assertEquals("Jane", actualUserRegistration.getFirstName());
		assertEquals("Doe", actualUserRegistration.getLastName());
		assertEquals("iloveyou", actualUserRegistration.getPassword());
		assertEquals("4105551212", actualUserRegistration.getPhone());
		assertEquals("Role", actualUserRegistration.getRole());
		assertEquals("42", actualUserRegistration.getId());
		assertEquals("janedoe", actualUserRegistration.getUsername());
		assertEquals(
				"UserRegistration(id=42, username=janedoe, password=iloveyou, email=jane.doe@example.org,"
						+ " phone=4105551212, firstName=Jane, lastName=Doe, dateOfBirth=1970-01-02, role=Role)",
				actualUserRegistration.toString());
	}

	@Test
	public void testEquals() {
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
		assertFalse(userRegistration.equals(null));
	}

	@Test
	public void testEquals10() {
		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setLastName("Doe");
		userRegistration.setPassword("iloveyou");
		userRegistration.setEmail(null);
		userRegistration.setRole("Role");
		userRegistration.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration.setId("42");
		userRegistration.setUsername("janedoe");
		userRegistration.setPhone("4105551212");
		userRegistration.setFirstName("Jane");

		UserRegistration userRegistration1 = new UserRegistration();
		userRegistration1.setLastName("Doe");
		userRegistration1.setPassword("iloveyou");
		userRegistration1.setEmail("jane.doe@example.org");
		userRegistration1.setRole("Role");
		userRegistration1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration1.setId("42");
		userRegistration1.setUsername("janedoe");
		userRegistration1.setPhone("4105551212");
		userRegistration1.setFirstName("Jane");
		assertFalse(userRegistration.equals(userRegistration1));
	}

	@Test
	public void testEquals11() {
		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setLastName("Doe");
		userRegistration.setPassword("iloveyou");
		userRegistration.setEmail("jane.doe@example.org");
		userRegistration.setRole("42");
		userRegistration.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration.setId("42");
		userRegistration.setUsername("janedoe");
		userRegistration.setPhone("4105551212");
		userRegistration.setFirstName("Jane");

		UserRegistration userRegistration1 = new UserRegistration();
		userRegistration1.setLastName("Doe");
		userRegistration1.setPassword("iloveyou");
		userRegistration1.setEmail("jane.doe@example.org");
		userRegistration1.setRole("Role");
		userRegistration1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration1.setId("42");
		userRegistration1.setUsername("janedoe");
		userRegistration1.setPhone("4105551212");
		userRegistration1.setFirstName("Jane");
		assertFalse(userRegistration.equals(userRegistration1));
	}

	@Test
	public void testEquals12() {
		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setLastName("Doe");
		userRegistration.setPassword("iloveyou");
		userRegistration.setEmail("jane.doe@example.org");
		userRegistration.setRole(null);
		userRegistration.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration.setId("42");
		userRegistration.setUsername("janedoe");
		userRegistration.setPhone("4105551212");
		userRegistration.setFirstName("Jane");

		UserRegistration userRegistration1 = new UserRegistration();
		userRegistration1.setLastName("Doe");
		userRegistration1.setPassword("iloveyou");
		userRegistration1.setEmail("jane.doe@example.org");
		userRegistration1.setRole("Role");
		userRegistration1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration1.setId("42");
		userRegistration1.setUsername("janedoe");
		userRegistration1.setPhone("4105551212");
		userRegistration1.setFirstName("Jane");
		assertFalse(userRegistration.equals(userRegistration1));
	}

	@Test
	public void testEquals13() {
		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setLastName("Doe");
		userRegistration.setPassword("iloveyou");
		userRegistration.setEmail("jane.doe@example.org");
		userRegistration.setRole("Role");
		userRegistration.setDateOfBirth(LocalDate.ofEpochDay(0L));
		userRegistration.setId("42");
		userRegistration.setUsername("janedoe");
		userRegistration.setPhone("4105551212");
		userRegistration.setFirstName("Jane");

		UserRegistration userRegistration1 = new UserRegistration();
		userRegistration1.setLastName("Doe");
		userRegistration1.setPassword("iloveyou");
		userRegistration1.setEmail("jane.doe@example.org");
		userRegistration1.setRole("Role");
		userRegistration1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration1.setId("42");
		userRegistration1.setUsername("janedoe");
		userRegistration1.setPhone("4105551212");
		userRegistration1.setFirstName("Jane");
		assertFalse(userRegistration.equals(userRegistration1));
	}

	@Test
	public void testEquals14() {
		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setLastName("Doe");
		userRegistration.setPassword("iloveyou");
		userRegistration.setEmail("jane.doe@example.org");
		userRegistration.setRole("Role");
		userRegistration.setDateOfBirth(null);
		userRegistration.setId("42");
		userRegistration.setUsername("janedoe");
		userRegistration.setPhone("4105551212");
		userRegistration.setFirstName("Jane");

		UserRegistration userRegistration1 = new UserRegistration();
		userRegistration1.setLastName("Doe");
		userRegistration1.setPassword("iloveyou");
		userRegistration1.setEmail("jane.doe@example.org");
		userRegistration1.setRole("Role");
		userRegistration1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration1.setId("42");
		userRegistration1.setUsername("janedoe");
		userRegistration1.setPhone("4105551212");
		userRegistration1.setFirstName("Jane");
		assertFalse(userRegistration.equals(userRegistration1));
	}

	@Test
	public void testEquals15() {
		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setLastName("Doe");
		userRegistration.setPassword("iloveyou");
		userRegistration.setEmail("jane.doe@example.org");
		userRegistration.setRole("Role");
		userRegistration.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration.setId(null);
		userRegistration.setUsername("janedoe");
		userRegistration.setPhone("4105551212");
		userRegistration.setFirstName("Jane");

		UserRegistration userRegistration1 = new UserRegistration();
		userRegistration1.setLastName("Doe");
		userRegistration1.setPassword("iloveyou");
		userRegistration1.setEmail("jane.doe@example.org");
		userRegistration1.setRole("Role");
		userRegistration1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration1.setId("42");
		userRegistration1.setUsername("janedoe");
		userRegistration1.setPhone("4105551212");
		userRegistration1.setFirstName("Jane");
		assertFalse(userRegistration.equals(userRegistration1));
	}

	@Test
	public void testEquals16() {
		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setLastName("Doe");
		userRegistration.setPassword("iloveyou");
		userRegistration.setEmail("jane.doe@example.org");
		userRegistration.setRole("Role");
		userRegistration.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration.setId("janedoe");
		userRegistration.setUsername("janedoe");
		userRegistration.setPhone("4105551212");
		userRegistration.setFirstName("Jane");

		UserRegistration userRegistration1 = new UserRegistration();
		userRegistration1.setLastName("Doe");
		userRegistration1.setPassword("iloveyou");
		userRegistration1.setEmail("jane.doe@example.org");
		userRegistration1.setRole("Role");
		userRegistration1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration1.setId("42");
		userRegistration1.setUsername("janedoe");
		userRegistration1.setPhone("4105551212");
		userRegistration1.setFirstName("Jane");
		assertFalse(userRegistration.equals(userRegistration1));
	}

	@Test
	public void testEquals17() {
		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setLastName("Doe");
		userRegistration.setPassword("iloveyou");
		userRegistration.setEmail("jane.doe@example.org");
		userRegistration.setRole("Role");
		userRegistration.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration.setId("42");
		userRegistration.setUsername("42");
		userRegistration.setPhone("4105551212");
		userRegistration.setFirstName("Jane");

		UserRegistration userRegistration1 = new UserRegistration();
		userRegistration1.setLastName("Doe");
		userRegistration1.setPassword("iloveyou");
		userRegistration1.setEmail("jane.doe@example.org");
		userRegistration1.setRole("Role");
		userRegistration1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration1.setId("42");
		userRegistration1.setUsername("janedoe");
		userRegistration1.setPhone("4105551212");
		userRegistration1.setFirstName("Jane");
		assertFalse(userRegistration.equals(userRegistration1));
	}

	@Test
	public void testEquals18() {
		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setLastName("Doe");
		userRegistration.setPassword("iloveyou");
		userRegistration.setEmail("jane.doe@example.org");
		userRegistration.setRole("Role");
		userRegistration.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration.setId("42");
		userRegistration.setUsername(null);
		userRegistration.setPhone("4105551212");
		userRegistration.setFirstName("Jane");

		UserRegistration userRegistration1 = new UserRegistration();
		userRegistration1.setLastName("Doe");
		userRegistration1.setPassword("iloveyou");
		userRegistration1.setEmail("jane.doe@example.org");
		userRegistration1.setRole("Role");
		userRegistration1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration1.setId("42");
		userRegistration1.setUsername("janedoe");
		userRegistration1.setPhone("4105551212");
		userRegistration1.setFirstName("Jane");
		assertFalse(userRegistration.equals(userRegistration1));
	}

	@Test
	public void testEquals19() {
		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setLastName("Doe");
		userRegistration.setPassword("iloveyou");
		userRegistration.setEmail("jane.doe@example.org");
		userRegistration.setRole("Role");
		userRegistration.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration.setId("42");
		userRegistration.setUsername("janedoe");
		userRegistration.setPhone("+44 1865 4960636");
		userRegistration.setFirstName("Jane");

		UserRegistration userRegistration1 = new UserRegistration();
		userRegistration1.setLastName("Doe");
		userRegistration1.setPassword("iloveyou");
		userRegistration1.setEmail("jane.doe@example.org");
		userRegistration1.setRole("Role");
		userRegistration1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration1.setId("42");
		userRegistration1.setUsername("janedoe");
		userRegistration1.setPhone("4105551212");
		userRegistration1.setFirstName("Jane");
		assertFalse(userRegistration.equals(userRegistration1));
	}

	@Test
	public void testEquals2() {
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
		assertFalse(userRegistration.equals("Different type to UserRegistration"));
	}

	@Test
	public void testEquals20() {
		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setLastName("Doe");
		userRegistration.setPassword("iloveyou");
		userRegistration.setEmail("jane.doe@example.org");
		userRegistration.setRole("Role");
		userRegistration.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration.setId("42");
		userRegistration.setUsername("janedoe");
		userRegistration.setPhone(null);
		userRegistration.setFirstName("Jane");

		UserRegistration userRegistration1 = new UserRegistration();
		userRegistration1.setLastName("Doe");
		userRegistration1.setPassword("iloveyou");
		userRegistration1.setEmail("jane.doe@example.org");
		userRegistration1.setRole("Role");
		userRegistration1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration1.setId("42");
		userRegistration1.setUsername("janedoe");
		userRegistration1.setPhone("4105551212");
		userRegistration1.setFirstName("Jane");
		assertFalse(userRegistration.equals(userRegistration1));
	}

	@Test
	public void testEquals21() {
		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setLastName("Doe");
		userRegistration.setPassword("iloveyou");
		userRegistration.setEmail("jane.doe@example.org");
		userRegistration.setRole("Role");
		userRegistration.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration.setId("42");
		userRegistration.setUsername("janedoe");
		userRegistration.setPhone("4105551212");
		userRegistration.setFirstName("42");

		UserRegistration userRegistration1 = new UserRegistration();
		userRegistration1.setLastName("Doe");
		userRegistration1.setPassword("iloveyou");
		userRegistration1.setEmail("jane.doe@example.org");
		userRegistration1.setRole("Role");
		userRegistration1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration1.setId("42");
		userRegistration1.setUsername("janedoe");
		userRegistration1.setPhone("4105551212");
		userRegistration1.setFirstName("Jane");
		assertFalse(userRegistration.equals(userRegistration1));
	}

	@Test
	public void testEquals22() {
		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setLastName("Doe");
		userRegistration.setPassword("iloveyou");
		userRegistration.setEmail("jane.doe@example.org");
		userRegistration.setRole("Role");
		userRegistration.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration.setId("42");
		userRegistration.setUsername("janedoe");
		userRegistration.setPhone("4105551212");
		userRegistration.setFirstName(null);

		UserRegistration userRegistration1 = new UserRegistration();
		userRegistration1.setLastName("Doe");
		userRegistration1.setPassword("iloveyou");
		userRegistration1.setEmail("jane.doe@example.org");
		userRegistration1.setRole("Role");
		userRegistration1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration1.setId("42");
		userRegistration1.setUsername("janedoe");
		userRegistration1.setPhone("4105551212");
		userRegistration1.setFirstName("Jane");
		assertFalse(userRegistration.equals(userRegistration1));
	}

	@Test
	public void testEquals3() {
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
		assertTrue(userRegistration.equals(userRegistration));
		int expectedHashCodeResult = userRegistration.hashCode();
		assertEquals(expectedHashCodeResult, userRegistration.hashCode());
	}

	@Test
	public void testEquals4() {
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

		UserRegistration userRegistration1 = new UserRegistration();
		userRegistration1.setLastName("Doe");
		userRegistration1.setPassword("iloveyou");
		userRegistration1.setEmail("jane.doe@example.org");
		userRegistration1.setRole("Role");
		userRegistration1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration1.setId("42");
		userRegistration1.setUsername("janedoe");
		userRegistration1.setPhone("4105551212");
		userRegistration1.setFirstName("Jane");
		assertTrue(userRegistration.equals(userRegistration1));
		int expectedHashCodeResult = userRegistration.hashCode();
		assertEquals(expectedHashCodeResult, userRegistration1.hashCode());
	}

	@Test
	public void testEquals5() {
		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setLastName("42");
		userRegistration.setPassword("iloveyou");
		userRegistration.setEmail("jane.doe@example.org");
		userRegistration.setRole("Role");
		userRegistration.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration.setId("42");
		userRegistration.setUsername("janedoe");
		userRegistration.setPhone("4105551212");
		userRegistration.setFirstName("Jane");

		UserRegistration userRegistration1 = new UserRegistration();
		userRegistration1.setLastName("Doe");
		userRegistration1.setPassword("iloveyou");
		userRegistration1.setEmail("jane.doe@example.org");
		userRegistration1.setRole("Role");
		userRegistration1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration1.setId("42");
		userRegistration1.setUsername("janedoe");
		userRegistration1.setPhone("4105551212");
		userRegistration1.setFirstName("Jane");
		assertFalse(userRegistration.equals(userRegistration1));
	}

	@Test
	public void testEquals6() {
		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setLastName(null);
		userRegistration.setPassword("iloveyou");
		userRegistration.setEmail("jane.doe@example.org");
		userRegistration.setRole("Role");
		userRegistration.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration.setId("42");
		userRegistration.setUsername("janedoe");
		userRegistration.setPhone("4105551212");
		userRegistration.setFirstName("Jane");

		UserRegistration userRegistration1 = new UserRegistration();
		userRegistration1.setLastName("Doe");
		userRegistration1.setPassword("iloveyou");
		userRegistration1.setEmail("jane.doe@example.org");
		userRegistration1.setRole("Role");
		userRegistration1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration1.setId("42");
		userRegistration1.setUsername("janedoe");
		userRegistration1.setPhone("4105551212");
		userRegistration1.setFirstName("Jane");
		assertFalse(userRegistration.equals(userRegistration1));
	}

	@Test
	public void testEquals7() {
		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setLastName("Doe");
		userRegistration.setPassword("42");
		userRegistration.setEmail("jane.doe@example.org");
		userRegistration.setRole("Role");
		userRegistration.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration.setId("42");
		userRegistration.setUsername("janedoe");
		userRegistration.setPhone("4105551212");
		userRegistration.setFirstName("Jane");

		UserRegistration userRegistration1 = new UserRegistration();
		userRegistration1.setLastName("Doe");
		userRegistration1.setPassword("iloveyou");
		userRegistration1.setEmail("jane.doe@example.org");
		userRegistration1.setRole("Role");
		userRegistration1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration1.setId("42");
		userRegistration1.setUsername("janedoe");
		userRegistration1.setPhone("4105551212");
		userRegistration1.setFirstName("Jane");
		assertFalse(userRegistration.equals(userRegistration1));
	}

	@Test
	public void testEquals8() {
		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setLastName("Doe");
		userRegistration.setPassword(null);
		userRegistration.setEmail("jane.doe@example.org");
		userRegistration.setRole("Role");
		userRegistration.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration.setId("42");
		userRegistration.setUsername("janedoe");
		userRegistration.setPhone("4105551212");
		userRegistration.setFirstName("Jane");

		UserRegistration userRegistration1 = new UserRegistration();
		userRegistration1.setLastName("Doe");
		userRegistration1.setPassword("iloveyou");
		userRegistration1.setEmail("jane.doe@example.org");
		userRegistration1.setRole("Role");
		userRegistration1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration1.setId("42");
		userRegistration1.setUsername("janedoe");
		userRegistration1.setPhone("4105551212");
		userRegistration1.setFirstName("Jane");
		assertFalse(userRegistration.equals(userRegistration1));
	}

	@Test
	public void testEquals9() {
		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setLastName("Doe");
		userRegistration.setPassword("iloveyou");
		userRegistration.setEmail("42");
		userRegistration.setRole("Role");
		userRegistration.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration.setId("42");
		userRegistration.setUsername("janedoe");
		userRegistration.setPhone("4105551212");
		userRegistration.setFirstName("Jane");

		UserRegistration userRegistration1 = new UserRegistration();
		userRegistration1.setLastName("Doe");
		userRegistration1.setPassword("iloveyou");
		userRegistration1.setEmail("jane.doe@example.org");
		userRegistration1.setRole("Role");
		userRegistration1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userRegistration1.setId("42");
		userRegistration1.setUsername("janedoe");
		userRegistration1.setPhone("4105551212");
		userRegistration1.setFirstName("Jane");
		assertFalse(userRegistration.equals(userRegistration1));
	}
}

