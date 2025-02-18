package com.gbm.cqrs.core.exception;

import java.io.Serial;

public class ConcurrencyException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1L;

	public ConcurrencyException() {
	}
	public ConcurrencyException(String message) {
		super(message);
	}
}
