package com.gbm;

import com.gbm.command.api.commands.*;
import com.gbm.cqrs.core.infrastructure.CommandDispatcher;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccountCommandServiceApplication {

	private final CommandDispatcher commandDispatcher;
	private final CommandHandler commandHandler;

	@Autowired
	public AccountCommandServiceApplication(CommandDispatcher commandDispatcher, CommandHandler commandHandler) {
		this.commandDispatcher = commandDispatcher;
		this.commandHandler = commandHandler;
	}

	public static void main(String[] args) {
		SpringApplication.run(AccountCommandServiceApplication.class, args);
	}

	@PostConstruct
	public void registerHandlers() {
		commandDispatcher.registerHandler(OpenAccountCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(DepositFundsCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(WithDrawFundsCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(CloseAccountCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(RestoreReadDbCommand.class, commandHandler::handle);
	}

}
