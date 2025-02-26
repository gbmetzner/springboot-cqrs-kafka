package com.gbm.command.api.controllers;

import com.gbm.account.common.dto.BaseResponse;
import com.gbm.command.api.commands.CloseAccountCommand;
import com.gbm.cqrs.core.infrastructure.CommandDispatcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/close-bank-account")
public class CloseAccountController {

	private static final Logger logger = LogManager.getLogger(CloseAccountController.class);

	private final CommandDispatcher commandDispatcher;

	@Autowired
	public CloseAccountController(CommandDispatcher dispatcher) {
		this.commandDispatcher = dispatcher;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<BaseResponse> openBankAccount(@PathVariable(value = "id") String id) {
		try {
			commandDispatcher.send(new CloseAccountCommand(id));
			return ResponseEntity.ok(new BaseResponse("Bank Account for id " + id + " closed successfully!"));
		} catch (IllegalStateException e) {
			logger.warn("Bad Request", e);
			return ResponseEntity.badRequest().body(new BaseResponse(e.toString()));
		} catch (Exception e) {
			var message = "Error while processing request to close bank account for id=" + id;
			logger.error(message, e);
			return ResponseEntity.internalServerError().body(new BaseResponse(message));
		}
	}

}
