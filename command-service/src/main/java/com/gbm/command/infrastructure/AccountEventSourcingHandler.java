package com.gbm.command.infrastructure;

import com.gbm.cqrs.core.domain.AggregateRoot;
import com.gbm.cqrs.core.event.BaseEvent;
import com.gbm.cqrs.core.handler.EventSourcingHandler;
import com.gbm.cqrs.core.infrastructure.EventStore;
import com.gbm.command.domain.AccountAggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Comparator.naturalOrder;

@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

	private final EventStore eventStore;

	@Autowired
	public AccountEventSourcingHandler(EventStore eventStore) {
		this.eventStore = eventStore;
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
}
