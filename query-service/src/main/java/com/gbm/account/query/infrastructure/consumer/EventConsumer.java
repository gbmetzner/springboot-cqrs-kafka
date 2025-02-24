package com.gbm.account.query.infrastructure.consumer;

import com.gbm.account.common.event.AccountClosedEvent;
import com.gbm.account.common.event.AccountOpenedEvent;
import com.gbm.account.common.event.FundsDepositedEvent;
import com.gbm.account.common.event.FundsWithdrawnEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {

	void consume(@Payload AccountOpenedEvent event, Acknowledgment acknowledgment);
	void consume(@Payload FundsDepositedEvent event, Acknowledgment acknowledgment);
	void consume(@Payload FundsWithdrawnEvent event, Acknowledgment acknowledgment);
	void consume(@Payload AccountClosedEvent event, Acknowledgment acknowledgment);

}
