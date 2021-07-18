package com.beardtrust.webapp.userservice.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * The type User registration.
 *
 * @author Matthew Crowell <Matthew.Crowell@Smoothstack.com>
 */
@Data
public class UserRegistration {

	private String userId;
	@NotBlank(message = "A username must be provided")
	private String username;
	@NotBlank(message = "A password must be provided")
	private String password;
	@NotBlank(message = "An email address must be provided")
	private String email;
	@NotBlank(message = "A phone number must be provided")
	private String phone;
	@NotBlank(message = "A first name must be provided")
	private String firstName;
	@NotBlank(message = "A surname must be provided")
	private String lastName;
	@NotNull(message = "A birthdate must be provided")
	private LocalDate dateOfBirth;
	private String role;
}
