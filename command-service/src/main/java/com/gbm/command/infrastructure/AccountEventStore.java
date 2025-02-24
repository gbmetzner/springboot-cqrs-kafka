package com.gbm.command.infrastructure;

import com.gbm.cqrs.core.event.BaseEvent;
import com.gbm.cqrs.core.event.EventModel;
import com.gbm.cqrs.core.exception.AggregateNotFoundException;
import com.gbm.cqrs.core.exception.ConcurrencyException;
import com.gbm.cqrs.core.infrastructure.EventStore;
import com.gbm.cqrs.core.producer.EventProducer;
import com.gbm.command.domain.AccountAggregate;
import com.gbm.command.domain.EventStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountEventStore implements EventStore {

	private final EventStoreRepository eventStoreRepository;
	private final EventProducer eventProducer;

	@Autowired
	public AccountEventStore(EventStoreRepository eventStoreRepository, EventProducer eventProducer) {
		this.eventStoreRepository = eventStoreRepository;
		this.eventProducer = eventProducer;
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
				eventProducer.produce(event.getClass().getSimpleName(), event);
			}
		}
	}

	@Override
	public List<BaseEvent> getEvents(String aggregateId) {
		var eventStream = eventStoreRepository.findByAggregateId(aggregateId);
		if (eventStream.isEmpty()) {
			throw new AggregateNotFoundException("Incorrect account ID provided");
		}
		return eventStream.stream().map(EventModel::getEventData).collect(Collectors.toList());
	}

}
