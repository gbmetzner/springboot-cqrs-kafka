package com.gbm.command.api.controller;

import com.gbm.account.common.dto.BaseResponse;
import com.gbm.command.api.cmd.WithDrawFundsCommand;
import com.gbm.cqrs.core.exception.AggregateNotFoundException;
import com.gbm.cqrs.core.infrastructure.CommandDispatcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/withdraw-funds")
public class WithdrawFundsController {

	private static final Logger logger = LogManager.getLogger(WithdrawFundsController.class);

	private final CommandDispatcher commandDispatcher;

	@Autowired
	public WithdrawFundsController(CommandDispatcher commandDispatcher) {
		this.commandDispatcher = commandDispatcher;
	}

	@PutMapping("/{id}")
	public ResponseEntity<BaseResponse> withFunds(@PathVariable(value = "id") String id,
			@RequestBody WithDrawFundsCommand command) {

		try {
			command.setId(id);
			commandDispatcher.send(command);
			return ResponseEntity.ok(new BaseResponse("Withdraw funds completed successfully!"));
		} catch (IllegalStateException | AggregateNotFoundException e) {
			logger.warn("Bad Request", e);
			return ResponseEntity.badRequest().body(new BaseResponse(e.toString()));
		} catch (Exception e) {
			var message = "Error while processing request to withdraw funds for bank account id=" + id;
			logger.error(message, e);
			return ResponseEntity.internalServerError().body(new BaseResponse(message));
		}
	}

}
