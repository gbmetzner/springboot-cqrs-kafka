package com.gbm.account.query.infrastructure.consumer;

import com.gbm.account.common.event.AccountClosedEvent;
import com.gbm.account.common.event.AccountOpenedEvent;
import com.gbm.account.common.event.FundsDepositedEvent;
import com.gbm.account.common.event.FundsWithdrawnEvent;
import com.gbm.account.query.infrastructure.handler.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class AccountEventConsumer implements EventConsumer {

	private final EventHandler eventHandler;

	@Autowired
	public AccountEventConsumer(EventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}

	@Override
	@KafkaListener(topics = "AccountOpenedEvent", groupId = "${spring.kafka.consumer.group-id}")
	public void consume(AccountOpenedEvent event, Acknowledgment acknowledgment) {
		eventHandler.on(event);
		acknowledgment.acknowledge();
	}

	@Override
	@KafkaListener(topics = "FundsDepositedEvent", groupId = "${spring.kafka.consumer.group-id}")
	public void consume(FundsDepositedEvent event, Acknowledgment acknowledgment) {
		eventHandler.on(event);
		acknowledgment.acknowledge();
	}

	@Override
	@KafkaListener(topics = "FundsWithdrawnEvent", groupId = "${spring.kafka.consumer.group-id}")
	public void consume(FundsWithdrawnEvent event, Acknowledgment acknowledgment) {
		eventHandler.on(event);
		acknowledgment.acknowledge();
	}

	@Override
	@KafkaListener(topics = "AccountClosedEvent", groupId = "${spring.kafka.consumer.group-id}")
	public void consume(AccountClosedEvent event, Acknowledgment acknowledgment) {
		eventHandler.on(event);
		acknowledgment.acknowledge();
	}
}
