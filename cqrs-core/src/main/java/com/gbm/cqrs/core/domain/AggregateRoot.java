package com.gbm.cqrs.core.domain;

import com.gbm.cqrs.core.event.BaseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot {

	private static final Logger LOGGER = LogManager.getLogger(AggregateRoot.class);

	protected String id;
	private int version = -1;

	private final List<BaseEvent> changeEvents = new ArrayList<>();

}
