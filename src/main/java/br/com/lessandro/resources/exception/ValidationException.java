package br.com.lessandro.resources.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter	
public class ValidationException extends RuntimeException {

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
	}

	public ValidationException(String entity, String attribute, String message) {
		super(message);
		this.entity = entity;
		this.attribute = attribute;
	}
}
