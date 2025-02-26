package com.gbm.account.query.api.controllers;

import com.gbm.account.query.api.dto.AccountLookupResponse;
import com.gbm.account.query.api.dto.EqualityType;
import com.gbm.account.query.api.query.FindAccountByHolderQuery;
import com.gbm.account.query.api.query.FindAccountByIdQuery;
import com.gbm.account.query.api.query.FindAccountWithBalanceQuery;
import com.gbm.account.query.api.query.FindAllAccountsQuery;
import com.gbm.account.query.domain.BankAccount;
import com.gbm.cqrs.core.infrastructure.QueryDispatcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bank-account-lookup")
public class AccountLookupController {

	private final static Logger logger = LogManager.getLogger(AccountLookupController.class);

	private final QueryDispatcher dispatcher;

	@Autowired
	public AccountLookupController(QueryDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	@GetMapping(path = "/")
	public ResponseEntity<AccountLookupResponse> getAllAccounts() {
		try {
			List<BankAccount> accounts = dispatcher.send(new FindAllAccountsQuery());
			if (accounts.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return ResponseEntity.ok(new AccountLookupResponse(accounts));
		} catch (Exception e) {
			var message = "Failed to retrieve all bank accounts";
			logger.error(message, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(AccountLookupResponse.builder().message(message).build());
		}
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable("id") String id) {
		try {
			List<BankAccount> accounts = dispatcher.send(new FindAccountByIdQuery(id));
			if (accounts.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return ResponseEntity.ok(new AccountLookupResponse(accounts));
		} catch (Exception e) {
			var message = "Failed to retrieve bank accounts by id";
			logger.error(message, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(AccountLookupResponse.builder().message(message).build());
		}
	}

	@GetMapping(path = "/by-holder/{holder}")
	public ResponseEntity<AccountLookupResponse> getAccountByHolder(@PathVariable("holder") String holder) {
		try {
			List<BankAccount> accounts = dispatcher.send(new FindAccountByHolderQuery(holder));
			if (accounts.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return ResponseEntity.ok(new AccountLookupResponse(accounts));
		} catch (Exception e) {
			var message = "Failed to retrieve bank accounts by holder";
			logger.error(message, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(AccountLookupResponse.builder().message(message).build());
		}
	}

	@GetMapping(path = "/with-balance/{equality-type}/{balance}")
	public ResponseEntity<AccountLookupResponse> getAccountWithBalance(
			@PathVariable("equality-type") EqualityType equalityType, @PathVariable("balance") BigDecimal balance) {
		try {
			List<BankAccount> accounts = dispatcher.send(new FindAccountWithBalanceQuery(equalityType, balance));
			if (accounts.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return ResponseEntity.ok(new AccountLookupResponse(accounts));
		} catch (Exception e) {
			var message = "Failed to retrieve bank accounts with balance";
			logger.error(message, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(AccountLookupResponse.builder().message(message).build());
		}
	}

}
