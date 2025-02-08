package com.gbm.account.common.event;

import com.gbm.cqrs.core.event.BaseEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AccountClosedEvent extends BaseEvent {
}
