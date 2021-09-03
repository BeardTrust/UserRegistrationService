package com.beardtrust.webapp.userservice.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class UserDTOTest {
	@Test
	public void testCanEqual() {
		assertFalse((new UserDTO()).canEqual("Other"));
	}

	@Test
	public void testCanEqual2() {
		UserDTO userDTO = new UserDTO();

		UserDTO userDTO1 = new UserDTO();
		userDTO1.setLastName("Doe");
		userDTO1.setEmail("jane.doe@example.org");
		userDTO1.setRole("Role");
		userDTO1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO1.setId("42");
		userDTO1.setUsername("janedoe");
		userDTO1.setPhone("4105551212");
		userDTO1.setFirstName("Jane");
		assertTrue(userDTO.canEqual(userDTO1));
	}

	@Test
	public void testConstructor() {
		UserDTO actualUserDTO = new UserDTO();
		LocalDate ofEpochDayResult = LocalDate.ofEpochDay(1L);
		actualUserDTO.setDateOfBirth(ofEpochDayResult);
		actualUserDTO.setEmail("jane.doe@example.org");
		actualUserDTO.setFirstName("Jane");
		actualUserDTO.setLastName("Doe");
		actualUserDTO.setPhone("4105551212");
		actualUserDTO.setRole("Role");
		actualUserDTO.setId("42");
		actualUserDTO.setUsername("janedoe");
		assertSame(ofEpochDayResult, actualUserDTO.getDateOfBirth());
		assertEquals("jane.doe@example.org", actualUserDTO.getEmail());
		assertEquals("Jane", actualUserDTO.getFirstName());
		assertEquals("Doe", actualUserDTO.getLastName());
		assertEquals("4105551212", actualUserDTO.getPhone());
		assertEquals("Role", actualUserDTO.getRole());
		assertEquals("42", actualUserDTO.getId());
		assertEquals("janedoe", actualUserDTO.getUsername());
	}

	@Test
	public void testEquals() {
		UserDTO userDTO = new UserDTO();
		userDTO.setLastName("Doe");
		userDTO.setEmail("jane.doe@example.org");
		userDTO.setRole("Role");
		userDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO.setId("42");
		userDTO.setUsername("janedoe");
		userDTO.setPhone("4105551212");
		userDTO.setFirstName("Jane");
		assertFalse(userDTO.equals(null));
	}

	@Test
	public void testEquals10() {
		UserDTO userDTO = new UserDTO();
		userDTO.setLastName("Doe");
		userDTO.setEmail("jane.doe@example.org");
		userDTO.setRole(null);
		userDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO.setId("42");
		userDTO.setUsername("janedoe");
		userDTO.setPhone("4105551212");
		userDTO.setFirstName("Jane");

		UserDTO userDTO1 = new UserDTO();
		userDTO1.setLastName("Doe");
		userDTO1.setEmail("jane.doe@example.org");
		userDTO1.setRole("Role");
		userDTO1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO1.setId("42");
		userDTO1.setUsername("janedoe");
		userDTO1.setPhone("4105551212");
		userDTO1.setFirstName("Jane");
		assertFalse(userDTO.equals(userDTO1));
	}

	@Test
	public void testEquals11() {
		UserDTO userDTO = new UserDTO();
		userDTO.setLastName("Doe");
		userDTO.setEmail("jane.doe@example.org");
		userDTO.setRole("Role");
		userDTO.setDateOfBirth(LocalDate.ofEpochDay(0L));
		userDTO.setId("42");
		userDTO.setUsername("janedoe");
		userDTO.setPhone("4105551212");
		userDTO.setFirstName("Jane");

		UserDTO userDTO1 = new UserDTO();
		userDTO1.setLastName("Doe");
		userDTO1.setEmail("jane.doe@example.org");
		userDTO1.setRole("Role");
		userDTO1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO1.setId("42");
		userDTO1.setUsername("janedoe");
		userDTO1.setPhone("4105551212");
		userDTO1.setFirstName("Jane");
		assertFalse(userDTO.equals(userDTO1));
	}

	@Test
	public void testEquals12() {
		UserDTO userDTO = new UserDTO();
		userDTO.setLastName("Doe");
		userDTO.setEmail("jane.doe@example.org");
		userDTO.setRole("Role");
		userDTO.setDateOfBirth(null);
		userDTO.setId("42");
		userDTO.setUsername("janedoe");
		userDTO.setPhone("4105551212");
		userDTO.setFirstName("Jane");

		UserDTO userDTO1 = new UserDTO();
		userDTO1.setLastName("Doe");
		userDTO1.setEmail("jane.doe@example.org");
		userDTO1.setRole("Role");
		userDTO1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO1.setId("42");
		userDTO1.setUsername("janedoe");
		userDTO1.setPhone("4105551212");
		userDTO1.setFirstName("Jane");
		assertFalse(userDTO.equals(userDTO1));
	}

	@Test
	public void testEquals13() {
		UserDTO userDTO = new UserDTO();
		userDTO.setLastName("Doe");
		userDTO.setEmail("jane.doe@example.org");
		userDTO.setRole("Role");
		userDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO.setId(null);
		userDTO.setUsername("janedoe");
		userDTO.setPhone("4105551212");
		userDTO.setFirstName("Jane");

		UserDTO userDTO1 = new UserDTO();
		userDTO1.setLastName("Doe");
		userDTO1.setEmail("jane.doe@example.org");
		userDTO1.setRole("Role");
		userDTO1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO1.setId("42");
		userDTO1.setUsername("janedoe");
		userDTO1.setPhone("4105551212");
		userDTO1.setFirstName("Jane");
		assertFalse(userDTO.equals(userDTO1));
	}

	@Test
	public void testEquals14() {
		UserDTO userDTO = new UserDTO();
		userDTO.setLastName("Doe");
		userDTO.setEmail("jane.doe@example.org");
		userDTO.setRole("Role");
		userDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO.setId("janedoe");
		userDTO.setUsername("janedoe");
		userDTO.setPhone("4105551212");
		userDTO.setFirstName("Jane");

		UserDTO userDTO1 = new UserDTO();
		userDTO1.setLastName("Doe");
		userDTO1.setEmail("jane.doe@example.org");
		userDTO1.setRole("Role");
		userDTO1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO1.setId("42");
		userDTO1.setUsername("janedoe");
		userDTO1.setPhone("4105551212");
		userDTO1.setFirstName("Jane");
		assertFalse(userDTO.equals(userDTO1));
	}

	@Test
	public void testEquals15() {
		UserDTO userDTO = new UserDTO();
		userDTO.setLastName("Doe");
		userDTO.setEmail("jane.doe@example.org");
		userDTO.setRole("Role");
		userDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO.setId("42");
		userDTO.setUsername("42");
		userDTO.setPhone("4105551212");
		userDTO.setFirstName("Jane");

		UserDTO userDTO1 = new UserDTO();
		userDTO1.setLastName("Doe");
		userDTO1.setEmail("jane.doe@example.org");
		userDTO1.setRole("Role");
		userDTO1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO1.setId("42");
		userDTO1.setUsername("janedoe");
		userDTO1.setPhone("4105551212");
		userDTO1.setFirstName("Jane");
		assertFalse(userDTO.equals(userDTO1));
	}

	@Test
	public void testEquals16() {
		UserDTO userDTO = new UserDTO();
		userDTO.setLastName("Doe");
		userDTO.setEmail("jane.doe@example.org");
		userDTO.setRole("Role");
		userDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO.setId("42");
		userDTO.setUsername(null);
		userDTO.setPhone("4105551212");
		userDTO.setFirstName("Jane");

		UserDTO userDTO1 = new UserDTO();
		userDTO1.setLastName("Doe");
		userDTO1.setEmail("jane.doe@example.org");
		userDTO1.setRole("Role");
		userDTO1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO1.setId("42");
		userDTO1.setUsername("janedoe");
		userDTO1.setPhone("4105551212");
		userDTO1.setFirstName("Jane");
		assertFalse(userDTO.equals(userDTO1));
	}

	@Test
	public void testEquals17() {
		UserDTO userDTO = new UserDTO();
		userDTO.setLastName("Doe");
		userDTO.setEmail("jane.doe@example.org");
		userDTO.setRole("Role");
		userDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO.setId("42");
		userDTO.setUsername("janedoe");
		userDTO.setPhone("+44 1865 4960636");
		userDTO.setFirstName("Jane");

		UserDTO userDTO1 = new UserDTO();
		userDTO1.setLastName("Doe");
		userDTO1.setEmail("jane.doe@example.org");
		userDTO1.setRole("Role");
		userDTO1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO1.setId("42");
		userDTO1.setUsername("janedoe");
		userDTO1.setPhone("4105551212");
		userDTO1.setFirstName("Jane");
		assertFalse(userDTO.equals(userDTO1));
	}

	@Test
	public void testEquals18() {
		UserDTO userDTO = new UserDTO();
		userDTO.setLastName("Doe");
		userDTO.setEmail("jane.doe@example.org");
		userDTO.setRole("Role");
		userDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO.setId("42");
		userDTO.setUsername("janedoe");
		userDTO.setPhone(null);
		userDTO.setFirstName("Jane");

		UserDTO userDTO1 = new UserDTO();
		userDTO1.setLastName("Doe");
		userDTO1.setEmail("jane.doe@example.org");
		userDTO1.setRole("Role");
		userDTO1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO1.setId("42");
		userDTO1.setUsername("janedoe");
		userDTO1.setPhone("4105551212");
		userDTO1.setFirstName("Jane");
		assertFalse(userDTO.equals(userDTO1));
	}

	@Test
	public void testEquals19() {
		UserDTO userDTO = new UserDTO();
		userDTO.setLastName("Doe");
		userDTO.setEmail("jane.doe@example.org");
		userDTO.setRole("Role");
		userDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO.setId("42");
		userDTO.setUsername("janedoe");
		userDTO.setPhone("4105551212");
		userDTO.setFirstName("42");

		UserDTO userDTO1 = new UserDTO();
		userDTO1.setLastName("Doe");
		userDTO1.setEmail("jane.doe@example.org");
		userDTO1.setRole("Role");
		userDTO1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO1.setId("42");
		userDTO1.setUsername("janedoe");
		userDTO1.setPhone("4105551212");
		userDTO1.setFirstName("Jane");
		assertFalse(userDTO.equals(userDTO1));
	}

	@Test
	public void testEquals2() {
		UserDTO userDTO = new UserDTO();
		userDTO.setLastName("Doe");
		userDTO.setEmail("jane.doe@example.org");
		userDTO.setRole("Role");
		userDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO.setId("42");
		userDTO.setUsername("janedoe");
		userDTO.setPhone("4105551212");
		userDTO.setFirstName("Jane");
		assertFalse(userDTO.equals("Different type to UserDTO"));
	}

	@Test
	public void testEquals20() {
		UserDTO userDTO = new UserDTO();
		userDTO.setLastName("Doe");
		userDTO.setEmail("jane.doe@example.org");
		userDTO.setRole("Role");
		userDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO.setId("42");
		userDTO.setUsername("janedoe");
		userDTO.setPhone("4105551212");
		userDTO.setFirstName(null);

		UserDTO userDTO1 = new UserDTO();
		userDTO1.setLastName("Doe");
		userDTO1.setEmail("jane.doe@example.org");
		userDTO1.setRole("Role");
		userDTO1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO1.setId("42");
		userDTO1.setUsername("janedoe");
		userDTO1.setPhone("4105551212");
		userDTO1.setFirstName("Jane");
		assertFalse(userDTO.equals(userDTO1));
	}

	@Test
	public void testEquals21() {
		UserDTO userDTO = new UserDTO();
		userDTO.setLastName(null);
		userDTO.setEmail("jane.doe@example.org");
		userDTO.setRole("Role");
		userDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO.setId("42");
		userDTO.setUsername("janedoe");
		userDTO.setPhone("4105551212");
		userDTO.setFirstName("Jane");

		UserDTO userDTO1 = new UserDTO();
		userDTO1.setLastName(null);
		userDTO1.setEmail("jane.doe@example.org");
		userDTO1.setRole("Role");
		userDTO1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO1.setId("42");
		userDTO1.setUsername("janedoe");
		userDTO1.setPhone("4105551212");
		userDTO1.setFirstName("Jane");
		assertTrue(userDTO.equals(userDTO1));
		int expectedHashCodeResult = userDTO.hashCode();
		assertEquals(expectedHashCodeResult, userDTO1.hashCode());
	}

	@Test
	public void testEquals22() {
		UserDTO userDTO = new UserDTO();
		userDTO.setLastName("Doe");
		userDTO.setEmail(null);
		userDTO.setRole("Role");
		userDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO.setId("42");
		userDTO.setUsername("janedoe");
		userDTO.setPhone("4105551212");
		userDTO.setFirstName("Jane");

		UserDTO userDTO1 = new UserDTO();
		userDTO1.setLastName("Doe");
		userDTO1.setEmail(null);
		userDTO1.setRole("Role");
		userDTO1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO1.setId("42");
		userDTO1.setUsername("janedoe");
		userDTO1.setPhone("4105551212");
		userDTO1.setFirstName("Jane");
		assertTrue(userDTO.equals(userDTO1));
		int expectedHashCodeResult = userDTO.hashCode();
		assertEquals(expectedHashCodeResult, userDTO1.hashCode());
	}

	@Test
	public void testEquals3() {
		UserDTO userDTO = new UserDTO();
		userDTO.setLastName("Doe");
		userDTO.setEmail("jane.doe@example.org");
		userDTO.setRole("Role");
		userDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO.setId("42");
		userDTO.setUsername("janedoe");
		userDTO.setPhone("4105551212");
		userDTO.setFirstName("Jane");
		assertTrue(userDTO.equals(userDTO));
		int expectedHashCodeResult = userDTO.hashCode();
		assertEquals(expectedHashCodeResult, userDTO.hashCode());
	}

	@Test
	public void testEquals4() {
		UserDTO userDTO = new UserDTO();
		userDTO.setLastName("Doe");
		userDTO.setEmail("jane.doe@example.org");
		userDTO.setRole("Role");
		userDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO.setId("42");
		userDTO.setUsername("janedoe");
		userDTO.setPhone("4105551212");
		userDTO.setFirstName("Jane");

		UserDTO userDTO1 = new UserDTO();
		userDTO1.setLastName("Doe");
		userDTO1.setEmail("jane.doe@example.org");
		userDTO1.setRole("Role");
		userDTO1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO1.setId("42");
		userDTO1.setUsername("janedoe");
		userDTO1.setPhone("4105551212");
		userDTO1.setFirstName("Jane");
		assertTrue(userDTO.equals(userDTO1));
		int expectedHashCodeResult = userDTO.hashCode();
		assertEquals(expectedHashCodeResult, userDTO1.hashCode());
	}

	@Test
	public void testEquals5() {
		UserDTO userDTO = new UserDTO();
		userDTO.setLastName("42");
		userDTO.setEmail("jane.doe@example.org");
		userDTO.setRole("Role");
		userDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO.setId("42");
		userDTO.setUsername("janedoe");
		userDTO.setPhone("4105551212");
		userDTO.setFirstName("Jane");

		UserDTO userDTO1 = new UserDTO();
		userDTO1.setLastName("Doe");
		userDTO1.setEmail("jane.doe@example.org");
		userDTO1.setRole("Role");
		userDTO1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO1.setId("42");
		userDTO1.setUsername("janedoe");
		userDTO1.setPhone("4105551212");
		userDTO1.setFirstName("Jane");
		assertFalse(userDTO.equals(userDTO1));
	}

	@Test
	public void testEquals6() {
		UserDTO userDTO = new UserDTO();
		userDTO.setLastName(null);
		userDTO.setEmail("jane.doe@example.org");
		userDTO.setRole("Role");
		userDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO.setId("42");
		userDTO.setUsername("janedoe");
		userDTO.setPhone("4105551212");
		userDTO.setFirstName("Jane");

		UserDTO userDTO1 = new UserDTO();
		userDTO1.setLastName("Doe");
		userDTO1.setEmail("jane.doe@example.org");
		userDTO1.setRole("Role");
		userDTO1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO1.setId("42");
		userDTO1.setUsername("janedoe");
		userDTO1.setPhone("4105551212");
		userDTO1.setFirstName("Jane");
		assertFalse(userDTO.equals(userDTO1));
	}

	@Test
	public void testEquals7() {
		UserDTO userDTO = new UserDTO();
		userDTO.setLastName("Doe");
		userDTO.setEmail("42");
		userDTO.setRole("Role");
		userDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO.setId("42");
		userDTO.setUsername("janedoe");
		userDTO.setPhone("4105551212");
		userDTO.setFirstName("Jane");

		UserDTO userDTO1 = new UserDTO();
		userDTO1.setLastName("Doe");
		userDTO1.setEmail("jane.doe@example.org");
		userDTO1.setRole("Role");
		userDTO1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO1.setId("42");
		userDTO1.setUsername("janedoe");
		userDTO1.setPhone("4105551212");
		userDTO1.setFirstName("Jane");
		assertFalse(userDTO.equals(userDTO1));
	}

	@Test
	public void testEquals8() {
		UserDTO userDTO = new UserDTO();
		userDTO.setLastName("Doe");
		userDTO.setEmail(null);
		userDTO.setRole("Role");
		userDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO.setId("42");
		userDTO.setUsername("janedoe");
		userDTO.setPhone("4105551212");
		userDTO.setFirstName("Jane");

		UserDTO userDTO1 = new UserDTO();
		userDTO1.setLastName("Doe");
		userDTO1.setEmail("jane.doe@example.org");
		userDTO1.setRole("Role");
		userDTO1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO1.setId("42");
		userDTO1.setUsername("janedoe");
		userDTO1.setPhone("4105551212");
		userDTO1.setFirstName("Jane");
		assertFalse(userDTO.equals(userDTO1));
	}

	@Test
	public void testEquals9() {
		UserDTO userDTO = new UserDTO();
		userDTO.setLastName("Doe");
		userDTO.setEmail("jane.doe@example.org");
		userDTO.setRole("42");
		userDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO.setId("42");
		userDTO.setUsername("janedoe");
		userDTO.setPhone("4105551212");
		userDTO.setFirstName("Jane");

		UserDTO userDTO1 = new UserDTO();
		userDTO1.setLastName("Doe");
		userDTO1.setEmail("jane.doe@example.org");
		userDTO1.setRole("Role");
		userDTO1.setDateOfBirth(LocalDate.ofEpochDay(1L));
		userDTO1.setId("42");
		userDTO1.setUsername("janedoe");
		userDTO1.setPhone("4105551212");
		userDTO1.setFirstName("Jane");
		assertFalse(userDTO.equals(userDTO1));
	}
}

