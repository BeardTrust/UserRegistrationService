package com.beardtrust.webapp.userservice.models;

import lombok.Data;

/**
 * The Registration Response for successful registrations.
 *
 * @author Matthew Crowell <Matthew.Crowell@Smoothstack.com>
 */
@Data
public class RegistrationResponse {
	private String userId;
}
