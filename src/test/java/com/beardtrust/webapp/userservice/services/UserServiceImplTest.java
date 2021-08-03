package com.beardtrust.webapp.userservice.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.beardtrust.webapp.userservice.entities.UserEntity;
import com.beardtrust.webapp.userservice.repos.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.data.domain.PageRequest;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {

    @MockBean
	private UserRepository userRepository;

    @Autowired
	private UserServiceImpl userServiceImpl;

    @Test
    public void testFindPaginated() {

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
        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(userEntity);
        Page<UserEntity> page = new PageImpl<UserEntity>(userEntities);

		when(this.userRepository.findAll(ArgumentMatchers.isA(Pageable.class))).thenReturn(page);

        var result = this.userServiceImpl.findPaginated(PageRequest.of(0, 50), null);
        assertEquals(1, result.getSize());
        assertNull(result.getContent().get(0).getPassword());
    }
}