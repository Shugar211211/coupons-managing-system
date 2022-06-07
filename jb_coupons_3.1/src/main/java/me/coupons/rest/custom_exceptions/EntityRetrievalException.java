package me.coupons.rest.custom_exceptions;

public class EntityRetrievalException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntityRetrievalException(String message, Throwable cause) {
		super(message, cause);
	}

	public EntityRetrievalException(String message) {
		super(message);
	}

	public EntityRetrievalException(Throwable cause) {
		super(cause);
	}
}
