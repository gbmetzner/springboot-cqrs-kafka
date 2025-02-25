package com.gbm.cqrs.core.domain;

import com.gbm.cqrs.core.events.BaseEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot {

	private static final Logger LOGGER = LogManager.getLogger(AggregateRoot.class);

	@Getter(value = AccessLevel.PUBLIC)
	protected String id;

	@Getter
	@Setter
	private int version = -1;

	private final List<BaseEvent> changeEvents = new ArrayList<>();

	public final List<BaseEvent> getUncommitedChange() {
		return changeEvents;
	}

	public final void markChangesAsCommited() {
		changeEvents.clear();
	}

	protected final void applyChange(BaseEvent event, boolean isNewEvent) {
		try {
			apply(event);
		} catch (Exception e) {
			LOGGER.error("apply event error", e);
		} finally {
			if (isNewEvent) {
				changeEvents.add(event);
			}
		}

	}

	public final void raiseEvent(BaseEvent event) {
		applyChange(event, true);
	}

	public final void replayEvent(Iterable<BaseEvent> events) {
		events.forEach(event -> applyChange(event, false));
	}

	public abstract void apply(BaseEvent event);
}
