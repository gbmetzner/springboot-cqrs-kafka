package com.gbm.cqrs.core.infrastructure;

import com.gbm.cqrs.core.domain.BaseEntity;
import com.gbm.cqrs.core.query.BaseQuery;
import com.gbm.cqrs.core.query.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {
	<T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handlerMethod);
	<U extends BaseEntity> List<U> send(BaseQuery query);
}
