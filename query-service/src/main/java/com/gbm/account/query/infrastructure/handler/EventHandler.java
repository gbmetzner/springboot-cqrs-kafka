package com.gbm.account.query.infrastructure.handler;

import com.gbm.account.common.event.AccountClosedEvent;
import com.gbm.account.common.event.AccountOpenedEvent;
import com.gbm.account.common.event.FundsDepositedEvent;
import com.gbm.account.common.event.FundsWithdrawnEvent;

public interface EventHandler {

	void on(AccountOpenedEvent event);
	void on(FundsDepositedEvent event);
	void on(FundsWithdrawnEvent event);
	void on(AccountClosedEvent event);

}
