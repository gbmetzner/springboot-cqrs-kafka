package com.gbm.cqrs.core.infrastructure;

import com.gbm.cqrs.core.commands.BaseCommand;
import com.gbm.cqrs.core.commands.CommandHandlerMethod;

public interface CommandDispatcher {

	<T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);
	<T extends BaseCommand> void send(T command);

}
