package com.gbm.command.infrastructure;

import com.gbm.cqrs.core.events.BaseEvent;
import com.gbm.cqrs.core.events.EventModel;
import com.gbm.cqrs.core.exceptions.AggregateNotFoundException;
import com.gbm.cqrs.core.exceptions.ConcurrencyException;
import com.gbm.cqrs.core.infrastructure.EventStore;
import com.gbm.cqrs.core.producers.EventProducer;
import com.gbm.command.domain.AccountAggregate;
import com.gbm.command.domain.EventStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class AccountEventStore implements EventStore {

	private final EventStoreRepository eventStoreRepository;
	private final EventProducer eventProducer;
	private final String topic;

	@Autowired
	public AccountEventStore(EventStoreRepository eventStoreRepository, EventProducer eventProducer,
			@Value("${spring.kafka.producer.topic}") String topic) {
		this.eventStoreRepository = eventStoreRepository;
		this.eventProducer = eventProducer;
		this.topic = topic;
	}

	@Override
	public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
		var eventStream = eventStoreRepository.findByAggregateId(aggregateId);
		if (expectedVersion != -1 && eventStream.getLast().getVersion() != expectedVersion) {
			throw new ConcurrencyException();
		}
		var version = expectedVersion;
		for (var event : events) {
			version++;
			event.setVersion(version);
			var eventModel = EventModel.builder().eventTime(OffsetDateTime.now()).aggregateId(aggregateId)
					.aggregateType(AccountAggregate.class.getName()).version(version)
					.eventType(event.getClass().getTypeName()).eventData(event).build();

			var persistedEvent = eventStoreRepository.save(eventModel);
			if (!persistedEvent.getId().isEmpty()) {
				eventProducer.produce(topic, event);
			}
		}
	}

	@Override
	public List<BaseEvent> getEvents(String aggregateId) {
		var eventStream = eventStoreRepository.findByAggregateId(aggregateId);
		if (eventStream.isEmpty()) {
			throw new AggregateNotFoundException("Incorrect account ID provided");
		}
		return eventStream.stream().map(EventModel::getEventData).toList();
	}

	@Override
	public List<String> getAggregateIds() {
		return eventStoreRepository.findAll().stream().map(EventModel::getAggregateId).distinct().toList();
	}

}
