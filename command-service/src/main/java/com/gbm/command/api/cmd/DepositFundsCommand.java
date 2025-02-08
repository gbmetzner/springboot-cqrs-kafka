package com.gbm.command.api.cmd;

import com.gbm.cqrs.core.command.BaseCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class DepositFundsCommand extends BaseCommand {
    private BigDecimal amount;
}
