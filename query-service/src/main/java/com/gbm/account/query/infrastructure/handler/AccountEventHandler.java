package com.gbm.account.query.infrastructure.handler;

import com.gbm.account.common.event.AccountClosedEvent;
import com.gbm.account.common.event.AccountOpenedEvent;
import com.gbm.account.common.event.FundsDepositedEvent;
import com.gbm.account.common.event.FundsWithdrawnEvent;
import com.gbm.account.query.domain.AccountRepository;
import com.gbm.account.query.domain.BankAccount;
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
	public void on(AccountOpenedEvent event) {

		var bankAccount = BankAccount.builder().id(event.getId()).accountHolder(event.getAccountHolder())
				.balance(event.getBalance()).accountType(event.getAccountType()).creationTime(event.getCreateTime())
				.build();

		accountRepository.save(bankAccount);

	}

	@Override
	public void on(FundsDepositedEvent event) {
		var bankAccount = accountRepository.findById(event.getId());
		bankAccount.ifPresent(account -> {
			account.setBalance(account.getBalance().add(event.getAmount()));
			accountRepository.save(account);
		});
	}

	@Override
	public void on(FundsWithdrawnEvent event) {
		var bankAccount = accountRepository.findById(event.getId());
		bankAccount.ifPresent(account -> {
			account.setBalance(account.getBalance().subtract(event.getAmount()));
			accountRepository.save(account);
		});
	}

	@Override
	public void on(AccountClosedEvent event) {
		var bankAccount = accountRepository.findById(event.getId());
		bankAccount.ifPresent(accountRepository::delete);
	}

}
