package com.gbm.command.api.commands;

public interface CommandHandler {
	void handle(OpenAccountCommand openAccountCommand);
	void handle(DepositFundsCommand depositFundsCommand);
	void handle(WithDrawFundsCommand withDrawFundsCommand);
	void handle(CloseAccountCommand closeAccountCommand);
	void handle(RestoreReadDbCommand restoreReadDbCommand);
}
