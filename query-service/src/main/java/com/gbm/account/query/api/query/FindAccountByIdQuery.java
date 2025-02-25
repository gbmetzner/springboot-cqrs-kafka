package com.gbm.account.query.api.query;

import com.gbm.cqrs.core.query.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FindAccountByIdQuery extends BaseQuery {
	private String id;
}
