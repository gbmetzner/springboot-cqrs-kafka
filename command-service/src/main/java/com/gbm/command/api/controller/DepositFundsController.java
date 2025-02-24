package com.gbm.command.api.controller;

import com.gbm.account.common.dto.BaseResponse;
import com.gbm.command.api.cmd.DepositFundsCommand;
import com.gbm.cqrs.core.exception.AggregateNotFoundException;
import com.gbm.cqrs.core.infrastructure.CommandDispatcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/deposit-funds")
public class DepositFundsController {

	private static final Logger logger = LogManager.getLogger(DepositFundsController.class);

	private final CommandDispatcher commandDispatcher;

	@Autowired
	public DepositFundsController(CommandDispatcher commandDispatcher) {
		this.commandDispatcher = commandDispatcher;
	}

	@PutMapping("/{id}")
	public ResponseEntity<BaseResponse> depositFunds(@PathVariable(value = "id") String id,
			@RequestBody DepositFundsCommand command) {

		try {
			command.setId(id);
			commandDispatcher.send(command);
			return ResponseEntity.ok(new BaseResponse("Deposit funds completed successfully!"));
		} catch (IllegalStateException | AggregateNotFoundException e) {
			logger.warn("Bad Request", e);
			return ResponseEntity.badRequest().body(new BaseResponse(e.toString()));
		} catch (Exception e) {
			var message = "Error while processing request to deposit funds for bank account id=" + id;
			logger.error(message, e);
			return ResponseEntity.internalServerError().body(new BaseResponse(message));
		}
	}

}
