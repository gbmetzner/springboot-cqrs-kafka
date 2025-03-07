package com.gbm.command.infrastructure;

import com.gbm.cqrs.core.events.BaseEvent;
import com.gbm.cqrs.core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccountEventProducer implements EventProducer {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	@Autowired
	public AccountEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@Override
	public void produce(String topic, BaseEvent baseEvent) {
		kafkaTemplate.send(topic, baseEvent);
	}
}
