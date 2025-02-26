package com.gbm;

import com.gbm.account.query.api.query.*;
import com.gbm.cqrs.core.infrastructure.QueryDispatcher;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccountQueryServiceApplication {

	private final QueryDispatcher dispatcher;
	private final QueryHandler queryHandler;

	@Autowired
	public AccountQueryServiceApplication(QueryDispatcher dispatcher, QueryHandler queryHandler) {
		this.dispatcher = dispatcher;
		this.queryHandler = queryHandler;
	}

	public static void main(String[] args) {
		SpringApplication.run(AccountQueryServiceApplication.class, args);
	}

	@PostConstruct
	public void registerHandlers() {
		dispatcher.registerHandler(FindAllAccountsQuery.class, queryHandler::handle);
		dispatcher.registerHandler(FindAccountByIdQuery.class, queryHandler::handle);
		dispatcher.registerHandler(FindAccountByHolderQuery.class, queryHandler::handle);
		dispatcher.registerHandler(FindAccountWithBalanceQuery.class, queryHandler::handle);
	}

}
