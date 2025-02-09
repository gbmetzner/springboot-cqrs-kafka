package com.gbm.account.common.event;

import com.gbm.account.common.dto.AccountType;
import com.gbm.cqrs.core.event.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AccountOpenedEvent extends BaseEvent {
	private String accountHolder;
	private AccountType accountType;
	private OffsetDateTime createTime;
	private BigDecimal balance;
}
