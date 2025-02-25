package com.gbm.cqrs.core.query;

import com.gbm.cqrs.core.domain.BaseEntity;

import java.util.List;

@FunctionalInterface
public interface QueryHandlerMethod<T extends BaseQuery> {
	List<BaseEntity> handle(T query);
}
