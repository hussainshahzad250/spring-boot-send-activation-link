package com.sas.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends Exception {

	private static final long serialVersionUID = -282913843145658333L;

	private int code;

	private HttpStatus httpStatus;

	public BadRequestException(String exceptionMessage) {
		super(exceptionMessage);
	}

	public BadRequestException(String exceptionMessage, HttpStatus httpStatus) {
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
