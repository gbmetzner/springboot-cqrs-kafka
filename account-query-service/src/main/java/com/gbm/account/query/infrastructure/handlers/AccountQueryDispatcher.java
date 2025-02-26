package com.gbm.account.query.infrastructure.handlers;

import com.gbm.cqrs.core.domain.BaseEntity;
import com.gbm.cqrs.core.infrastructure.QueryDispatcher;
import com.gbm.cqrs.core.query.BaseQuery;
import com.gbm.cqrs.core.query.QueryHandlerMethod;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountQueryDispatcher implements QueryDispatcher {

	private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes = new HashMap<>();

	@Override
	public <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handlerMethod) {
		var handlers = routes.computeIfAbsent(type, t -> new LinkedList<>());
		handlers.add(handlerMethod);
	}

	@Override
	public <U extends BaseEntity> List<U> send(BaseQuery query) {
		var handlers = routes.get(query.getClass());
		if (handlers == null || handlers.isEmpty()) {
			throw new RuntimeException("No handler for " + query.getClass() + " found");
		}
		return handlers.getFirst().handle(query);
	}
}
