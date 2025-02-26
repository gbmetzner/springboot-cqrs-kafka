package com.gbm.account.query.infrastructure.handlers;

import com.gbm.cqrs.core.events.BaseEvent;

public interface EventHandler {
	void on(BaseEvent event);
}
