package com.gbm.command.api.controllers;

import com.gbm.account.common.dto.BaseResponse;
import com.gbm.command.api.commands.RestoreReadDbCommand;
import com.gbm.cqrs.core.infrastructure.CommandDispatcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/restore-read-db")
public class RestoreReadDbController {

	private static final Logger logger = LogManager.getLogger(RestoreReadDbController.class);

	private final CommandDispatcher commandDispatcher;

	@Autowired
	public RestoreReadDbController(CommandDispatcher dispatcher) {
		this.commandDispatcher = dispatcher;
	}

	@PostMapping
	public ResponseEntity<BaseResponse> restoreReadDb() {
		try {
			commandDispatcher.send(new RestoreReadDbCommand());
			return new ResponseEntity<>(new BaseResponse("Bank account DB restored!"), HttpStatus.CONFLICT);
		} catch (IllegalStateException e) {
			logger.warn("Bad Request", e);
			return ResponseEntity.badRequest().body(new BaseResponse(e.toString()));
		} catch (Exception e) {
			var message = "Error while processing request restore read db!";
			logger.error(message, e);
			return ResponseEntity.internalServerError().body(new BaseResponse(message));
		}
	}
}
