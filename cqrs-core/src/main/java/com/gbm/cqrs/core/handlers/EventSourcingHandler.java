package com.gbm.cqrs.core.handlers;

import com.gbm.cqrs.core.domain.AggregateRoot;

public interface EventSourcingHandler<T> {
	void save(AggregateRoot aggregate);
	T getById(String id);

	void republishEvents();
}
