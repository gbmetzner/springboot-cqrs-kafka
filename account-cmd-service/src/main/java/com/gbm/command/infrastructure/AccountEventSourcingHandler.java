package com.gbm.command.infrastructure;

import com.gbm.cqrs.core.domain.AggregateRoot;
import com.gbm.cqrs.core.events.BaseEvent;
import com.gbm.cqrs.core.handlers.EventSourcingHandler;
import com.gbm.cqrs.core.infrastructure.EventStore;
import com.gbm.command.domain.AccountAggregate;
import com.gbm.cqrs.core.producers.EventProducer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static java.util.Comparator.naturalOrder;

@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

	private final static Logger logger = LogManager.getLogger(AccountEventSourcingHandler.class);

	private final EventStore eventStore;
	private final EventProducer eventProducer;
	private final String topic;

	@Autowired
	public AccountEventSourcingHandler(EventStore eventStore, EventProducer eventProducer,
			@Value("${spring.kafka.producer.topic}") String topic) {
		this.eventStore = eventStore;
		this.eventProducer = eventProducer;
		this.topic = topic;
	}

	@Override
	public void save(AggregateRoot aggregate) {
		eventStore.saveEvents(aggregate.getId(), aggregate.getUncommitedChange(), aggregate.getVersion());
		aggregate.markChangesAsCommited();
	}

	@Override
	public AccountAggregate getById(String id) {
		var aggregate = new AccountAggregate();
		var events = eventStore.getEvents(id);

		if (events != null && !events.isEmpty()) {
			aggregate.replayEvent(events);
			aggregate.setVersion(events.stream().map(BaseEvent::getVersion).max(naturalOrder()).orElse(0));
		}

		return aggregate;
	}

	@Override
	public void republishEvents() {
		var aggregateIds = eventStore.getAggregateIds();
		if (aggregateIds.isEmpty()) {
			logger.info("No events found for restore process");
			return;
		}
		aggregateIds.stream().map(this::getById).filter(AccountAggregate::isActive)
				.map(accountAggregate -> eventStore.getEvents(accountAggregate.getId()))
				.forEach(events -> events.forEach(event -> eventProducer.produce(topic, event)));
	}
}
