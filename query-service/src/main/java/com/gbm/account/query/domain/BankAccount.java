package com.gbm.account.query.domain;

import com.gbm.account.common.dto.AccountType;
import com.gbm.cqrs.core.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class BankAccount extends BaseEntity {
	@Id
	private String id;
	private String accountHolder;
	private OffsetDateTime creationTime;
	private AccountType accountType;
	private BigDecimal balance;
}
