package com.gbm.domain;

import com.gbm.account.common.event.AccountClosedEvent;
import com.gbm.account.common.event.AccountOpenedEvent;
import com.gbm.account.common.event.FundsDepositedEvent;
import com.gbm.account.common.event.FundsWithdrawnEvent;
import com.gbm.command.api.cmd.OpenAccountCommand;
import com.gbm.cqrs.core.domain.AggregateRoot;
import com.gbm.cqrs.core.event.BaseEvent;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {

	private Boolean active;
	private BigDecimal balance;

	public AccountAggregate(OpenAccountCommand command) {

		var accountOpenedEvent = AccountOpenedEvent.builder().id(command.getId())
				.accountHolder(command.getAccountHolder()).accountType(command.getAccountType())
				.createTime(OffsetDateTime.now()).balance(command.getBalance()).build();

		raiseEvent(accountOpenedEvent);

	}

	@Override
	public void apply(BaseEvent event) {
		switch (event) {
			case AccountOpenedEvent accountOpenedEvent :
				applyAccountOpenedEvent(accountOpenedEvent);
				break;
			case FundsDepositedEvent fundsDepositedEvent :
				applyFundsDepositEvent(fundsDepositedEvent);
				break;
			case FundsWithdrawnEvent fundsWithdrawnEvent :
				applyFundsWithdrawnEvent(fundsWithdrawnEvent);
				break;
			case AccountClosedEvent accountClosedEvent :
				applyAccountClosedEvent(accountClosedEvent);
				break;
			default :
				throw new IllegalStateException(
						String.format("Unexpected value %s for event", event.getClass().getName()));
		}
	}

	private void applyAccountOpenedEvent(AccountOpenedEvent event) {
		this.id = event.getId();
		this.active = true;
		this.balance = event.getBalance();
	}

	private void applyFundsDepositEvent(FundsDepositedEvent event) {
		this.id = event.getId();
		this.balance = balance.add(event.getAmount());
	}

	public void applyFundsWithdrawnEvent(FundsWithdrawnEvent event) {
		this.id = event.getId();
		this.balance = this.balance.subtract(event.getAmount());
	}

	public void applyAccountClosedEvent(AccountClosedEvent event) {
		this.id = event.getId();
		this.active = false;
	}

}
