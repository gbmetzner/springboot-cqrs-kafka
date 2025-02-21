package com.gbm.infrastructure;

import com.gbm.cqrs.core.command.BaseCommand;
import com.gbm.cqrs.core.command.CommandHandlerMethod;
import com.gbm.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {

	private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();

	@Override
	public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
		var handlers = routes.computeIfAbsent(type, k -> new LinkedList<>());
		handlers.add(handler);
	}

	@Override
	public <T extends BaseCommand> void send(T command) {
		var handlers = routes.get(command.getClass());

		if (handlers == null || handlers.isEmpty()) {
			throw new RuntimeException("No handler found for command: " + command.getClass());
		}
		if (handlers.size() > 1) {
			throw new RuntimeException("Multiple handler found for command: " + command.getClass());
		}

		handlers.getFirst().handle(command);
	}
}
