package com.gbm.cqrs.core.command;

@FunctionalInterface
public interface CommandHandlerMethod<T extends BaseCommand> {
	void handle(T command);
}
