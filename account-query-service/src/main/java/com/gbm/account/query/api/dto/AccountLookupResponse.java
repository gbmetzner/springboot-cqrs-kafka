package com.gbm.account.query.api.dto;

import com.gbm.account.common.dto.BaseResponse;
import com.gbm.account.query.domain.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AccountLookupResponse extends BaseResponse {

	private List<BankAccount> bankAccounts;

	public AccountLookupResponse(String message) {
		super(message);
	}

}
