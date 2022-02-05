package com.jb.coupons_project.rest.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jb.coupons_project.rest.custom_exceptions.EntityRetrievalException;
import com.jb.coupons_project.rest.error_response_entity.ErrorResponse;

@ControllerAdvice
public class RestExceptionHandler {
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(EntityRetrievalException exc) {
		
		ErrorResponse error = new ErrorResponse();
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(Exception exc) {
		
		ErrorResponse error = new ErrorResponse();
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
//	@ExceptionHandler
//	public ResponseEntity<ErrorResponse> handleException(WrongCredentialsException exc) {
//		
//		ErrorResponse error = new ErrorResponse();
//		error.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
//		error.setMessage(exc.getMessage());
//		error.setTimeStamp(System.currentTimeMillis());
//		
//		return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
//	}
//	
//	@ExceptionHandler
//	public ResponseEntity<ErrorResponse> handleException(AuthenticationException exc) {
//		
//		ErrorResponse error = new ErrorResponse();
//		error.setStatus(HttpStatus.UNAUTHORIZED.value());
//		error.setMessage(exc.getMessage());
//		error.setTimeStamp(System.currentTimeMillis());
//		
//		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
//	}
//	
//	@ExceptionHandler
//	public ResponseEntity<ErrorResponse> handleException(AuthorizationException exc) {
//		
//		ErrorResponse error = new ErrorResponse();
//		error.setStatus(HttpStatus.FORBIDDEN.value());
//		error.setMessage(exc.getMessage());
//		error.setTimeStamp(System.currentTimeMillis());
//		
//		return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
//	}
}
