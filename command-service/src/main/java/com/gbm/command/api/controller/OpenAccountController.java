package com.gbm.command.api.controller;

import com.gbm.account.common.dto.BaseResponse;
import com.gbm.command.api.cmd.OpenAccountCommand;
import com.gbm.command.api.dto.OpenAccountResponse;
import com.gbm.cqrs.core.infrastructure.CommandDispatcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/open-bank-account")
public class OpenAccountController {

	private static final Logger logger = LogManager.getLogger(OpenAccountController.class);

	private final CommandDispatcher commandDispatcher;

	@Autowired
	public OpenAccountController(CommandDispatcher dispatcher) {
		this.commandDispatcher = dispatcher;
	}

	@PostMapping
	public ResponseEntity<BaseResponse> openBankAccount(@RequestBody OpenAccountCommand command) {
		var id = UUID.randomUUID().toString();
		command.setId(id);
		try {
			commandDispatcher.send(command);
			return ResponseEntity.created(new URI("/api/v1/open-bank-account/" + id))
					.body(new OpenAccountResponse("Bank account creation completed!", id));
		} catch (IllegalStateException e) {
			logger.warn("Bad Request", e);
			return ResponseEntity.badRequest().body(new BaseResponse(e.toString()));
		} catch (Exception e) {
			var message = "Error while processing request to open bank account for id=" + id;
			logger.error(message, e);
			return ResponseEntity.internalServerError().body(new BaseResponse(message));
		}
	}

}
