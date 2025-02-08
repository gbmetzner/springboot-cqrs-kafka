package com.gbm.command.api.cmd;

import com.gbm.cqrs.core.command.BaseCommand;

public class CloseAccountCommand extends BaseCommand {
    public CloseAccountCommand(String id) {
        super(id);
    }
}
