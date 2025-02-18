package com.gbm.cqrs.core.event;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;

@Data
@Builder
@Document(collection = "eventStoreDB")
public class EventModel {

	@Id
	private String id;
	private OffsetDateTime eventTime;
	private String aggregateId;
	private String aggregateType;
	private int version;
	private String eventType;
	private BaseEvent eventData;

}
