package com.gbm.command.api.dto;

import com.gbm.account.common.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OpenAccountResponse extends BaseResponse {

	private String id;

	public OpenAccountResponse(String message, String id) {
		super(message);
		this.id = id;
	}
}
