package com.gbm.account.query.api.dto;

import lombok.Getter;

public enum EqualityType {
	GT("GreaterThan"), LT("LessThan");

	@Getter
	private final String description;

	EqualityType(String description) {
		this.description = description;
	}
}
