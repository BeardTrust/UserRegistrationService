package com.beardtrust.webapp.userservice.models;

import lombok.Data;

/**
 * The type Login request model.
 *
 * @author Matthew Crowell <Matthew.Crowell@Smoothstack.com>
 */
@Data
public class LoginRequestModel {
	private String email;
	private String password;
	private String role;
}