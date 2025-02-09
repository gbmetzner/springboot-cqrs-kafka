package com.gbm.cqrs.core.command;

import com.gbm.cqrs.core.message.Message;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class BaseCommand extends Message {

	public BaseCommand(String id) {
		super(id);
	}

}
