package com.sas.exception;

import org.springframework.http.HttpStatus;

public class ObjectNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int code;

	private HttpStatus httpStatus;
	
	

	public ObjectNotFoundException(String exceptionMessage) {
		super(exceptionMessage);
	}

	public ObjectNotFoundException(String exceptionMessage, HttpStatus httpStatus) {
		super(exceptionMessage);
		this.code = httpStatus.value();
		this.httpStatus = httpStatus;
	}

	public int getCode() {
		return code;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

}
