package com.gbm.account.query.api.query;

import com.gbm.account.query.api.dto.EqualityType;
import com.gbm.cqrs.core.query.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FindAccountWithBalanceQuery extends BaseQuery {
	private EqualityType equalityType;
	private BigDecimal balance;
}
