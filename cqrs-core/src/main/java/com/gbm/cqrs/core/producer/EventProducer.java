package com.gbm.cqrs.core.producer;

import com.gbm.cqrs.core.event.BaseEvent;

public interface EventProducer {
	void produce(String topic, BaseEvent baseEvent);
}
