package com.gbm.cqrs.core.producers;

import com.gbm.cqrs.core.events.BaseEvent;

public interface EventProducer {
	void produce(String topic, BaseEvent baseEvent);
}
