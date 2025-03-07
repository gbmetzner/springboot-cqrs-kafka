package com.gbm.cqrs.core.exceptions;

import java.io.Serial;

public class AggregateNotFoundException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1L;

	public AggregateNotFoundException(String message) {
		super(message);
	}
}
