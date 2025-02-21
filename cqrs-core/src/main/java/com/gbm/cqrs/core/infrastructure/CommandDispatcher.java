package com.gbm.cqrs.core.infrastructure;

import com.gbm.cqrs.core.command.BaseCommand;
import com.gbm.cqrs.core.command.CommandHandlerMethod;

public interface CommandDispatcher {

	<T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);
	<T extends BaseCommand> void send(T command);

}
