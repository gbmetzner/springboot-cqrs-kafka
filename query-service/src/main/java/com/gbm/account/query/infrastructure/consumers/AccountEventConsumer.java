package com.gbm.account.query.infrastructure.consumers;

import com.gbm.account.query.infrastructure.handlers.EventHandler;
import com.gbm.cqrs.core.events.BaseEvent;
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
	@KafkaListener(topics = "${spring.kafka.consumer.topic}", groupId = "${spring.kafka.consumer.group-id}")
	public void consume(BaseEvent event, Acknowledgment acknowledgment) {
		eventHandler.on(event);
		acknowledgment.acknowledge();
	}
}
