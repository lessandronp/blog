package br.com.lessandro.resources.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter	
public class ValidationException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private HttpStatus status;
	private Long timestamp;
	private String error;
	private String message;
	private String entity;
	private String attribute;
	
	public ValidationException(String message) {
		super(message);
	}
	
	public ValidationException(Long timestamp, HttpStatus status, String error, String message) {
		super(message);
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.message = message;
	}

	public ValidationException(String entity, String attribute, HttpStatus status, String message) {
		super(message);
		this.entity = entity;
		this.attribute = attribute;
		this.status = status;
		this.message = message;
	}
}
