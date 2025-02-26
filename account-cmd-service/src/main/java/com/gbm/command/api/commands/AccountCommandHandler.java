package com.gbm.command.api.commands;

import com.gbm.cqrs.core.handlers.EventSourcingHandler;
import com.gbm.command.domain.AccountAggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountCommandHandler implements CommandHandler {

	private final EventSourcingHandler<AccountAggregate> eventSourcingHandler;

	@Autowired
	public AccountCommandHandler(EventSourcingHandler<AccountAggregate> eventSourcingHandler) {
		this.eventSourcingHandler = eventSourcingHandler;
	}

	@Override
	public void handle(OpenAccountCommand openAccountCommand) {
		var aggregate = new AccountAggregate(openAccountCommand);
		eventSourcingHandler.save(aggregate);
	}

	@Override
	public void handle(DepositFundsCommand depositFundsCommand) {
		var aggregate = eventSourcingHandler.getById(depositFundsCommand.getId());
		aggregate.depositFunds(depositFundsCommand.getAmount());
		eventSourcingHandler.save(aggregate);
	}

	@Override
	public void handle(WithDrawFundsCommand withDrawFundsCommand) {
		var aggregate = eventSourcingHandler.getById(withDrawFundsCommand.getId());
		if (withDrawFundsCommand.getAmount().compareTo(aggregate.getBalance()) > 0) {
			throw new IllegalArgumentException("Withdrawn declined, insufficient funds");
		}
		aggregate.withdrawFunds(withDrawFundsCommand.getAmount());
		eventSourcingHandler.save(aggregate);
	}

	@Override
	public void handle(CloseAccountCommand closeAccountCommand) {
		var aggregate = eventSourcingHandler.getById(closeAccountCommand.getId());
		aggregate.closeAccount();
		eventSourcingHandler.save(aggregate);
	}

	@Override
	public void handle(RestoreReadDbCommand restoreReadDbCommand) {
		eventSourcingHandler.republishEvents();
	}
}
