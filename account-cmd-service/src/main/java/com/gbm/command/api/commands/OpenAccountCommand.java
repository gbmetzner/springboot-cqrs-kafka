package com.gbm.command.api.commands;

import com.gbm.account.common.dto.AccountType;
import com.gbm.cqrs.core.commands.BaseCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class OpenAccountCommand extends BaseCommand {
	private String accountHolder;
	private AccountType accountType;
	private BigDecimal balance;
}
