package com.gbm.command.api.commands;

import com.gbm.cqrs.core.commands.BaseCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class WithDrawFundsCommand extends BaseCommand {
	private BigDecimal amount;
}
