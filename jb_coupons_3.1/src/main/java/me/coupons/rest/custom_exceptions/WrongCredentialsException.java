package me.coupons.rest.custom_exceptions;

public class WrongCredentialsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WrongCredentialsException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongCredentialsException(String message) {
		super(message);
	}

	public WrongCredentialsException(Throwable cause) {
		super(cause);
	}
}
