package com.crudcode.exception;

public class EmployeeNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5553820502456566005L;

	public EmployeeNotFoundException(String message) {
		super(message);
	}

}
