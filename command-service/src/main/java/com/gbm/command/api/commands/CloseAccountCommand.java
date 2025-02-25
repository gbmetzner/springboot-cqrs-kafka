package com.gbm.command.api.commands;

import com.gbm.cqrs.core.commands.BaseCommand;

public class CloseAccountCommand extends BaseCommand {
	public CloseAccountCommand(String id) {
		super(id);
	}
}
