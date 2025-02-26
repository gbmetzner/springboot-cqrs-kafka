package com.gbm.account.query.infrastructure.handlers;

import com.gbm.account.common.events.AccountClosedEvent;
import com.gbm.account.common.events.AccountOpenedEvent;
import com.gbm.account.common.events.FundsDepositedEvent;
import com.gbm.account.common.events.FundsWithdrawnEvent;
import com.gbm.account.query.domain.AccountRepository;
import com.gbm.account.query.domain.BankAccount;
import com.gbm.cqrs.core.events.BaseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountEventHandler implements EventHandler {

	private final AccountRepository accountRepository;

	@Autowired
	public AccountEventHandler(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public void on(BaseEvent event) {
		switch (event) {
			case AccountOpenedEvent accountOpenedEvent :
				on(accountOpenedEvent);
				break;
			case FundsDepositedEvent fundsDepositedEvent :
				on(fundsDepositedEvent);
				break;
			case FundsWithdrawnEvent fundsWithdrawnEvent :
				on(fundsWithdrawnEvent);
				break;
			case AccountClosedEvent accountClosedEvent :
				on(accountClosedEvent);
				break;
			default :
				throw new IllegalStateException(
						String.format("Unexpected value %s for event", event.getClass().getName()));
		}

	}

	private void on(AccountOpenedEvent event) {
		var bankAccount = BankAccount.builder().id(event.getId()).accountHolder(event.getAccountHolder())
				.balance(event.getBalance()).accountType(event.getAccountType()).creationTime(event.getCreateTime())
				.build();

		accountRepository.save(bankAccount);
	}

	private void on(FundsDepositedEvent event) {
		var bankAccount = accountRepository.findById(event.getId());
		bankAccount.ifPresent(account -> {
			account.setBalance(account.getBalance().add(event.getAmount()));
			accountRepository.save(account);
		});
	}

	private void on(FundsWithdrawnEvent event) {
		var bankAccount = accountRepository.findById(event.getId());
		bankAccount.ifPresent(account -> {
			account.setBalance(account.getBalance().subtract(event.getAmount()));
			accountRepository.save(account);
		});
	}

	private void on(AccountClosedEvent event) {
		var bankAccount = accountRepository.findById(event.getId());
		bankAccount.ifPresent(accountRepository::delete);
	}

}
