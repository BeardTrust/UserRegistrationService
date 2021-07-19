package com.beardtrust.webapp.userservice.exceptions;

/**
 * The type Duplicate entry exception.
 *
 * @author Matthew Crowell <Matthew.Crowell@Smoothstack.com>
 */
public class DuplicateEntryException extends RuntimeException {
	/**
	 * Instantiates a new Duplicate entry exception.
	 *
	 * @param msg String the associated error message
	 */
	public DuplicateEntryException(String msg){
		super(msg);
	}
}
