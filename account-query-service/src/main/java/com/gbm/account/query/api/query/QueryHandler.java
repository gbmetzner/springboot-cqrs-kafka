package com.gbm.account.query.api.query;

import com.gbm.cqrs.core.domain.BaseEntity;

import java.util.List;

public interface QueryHandler {
	List<BaseEntity> handle(FindAllAccountsQuery query);
	List<BaseEntity> handle(FindAccountByIdQuery query);
	List<BaseEntity> handle(FindAccountByHolderQuery query);
	List<BaseEntity> handle(FindAccountWithBalanceQuery query);
}
