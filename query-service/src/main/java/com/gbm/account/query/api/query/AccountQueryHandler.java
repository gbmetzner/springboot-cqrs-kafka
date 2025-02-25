package com.gbm.account.query.api.query;

import com.gbm.account.query.api.dto.EqualityType;
import com.gbm.account.query.domain.AccountRepository;
import com.gbm.cqrs.core.domain.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class AccountQueryHandler implements QueryHandler {

	private final AccountRepository accountRepository;

	@Autowired
	public AccountQueryHandler(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public List<BaseEntity> handle(FindAllAccountsQuery query) {
		return StreamSupport.stream(accountRepository.findAll().spliterator(), false).map(a -> (BaseEntity) a).toList();
	}

	@Override
	public List<BaseEntity> handle(FindAccountByIdQuery query) {
		return accountRepository.findById(query.getId()).map(a -> ((BaseEntity) a)).map(List::of).orElse(List.of());
	}

	@Override
	public List<BaseEntity> handle(FindAccountByHolderQuery query) {
		return accountRepository.findByAccountHolder(query.getAccountHolder()).map(a -> ((BaseEntity) a)).map(List::of)
				.orElse(List.of());
	}

	@Override
	public List<BaseEntity> handle(FindAccountWithBalanceQuery query) {
		if (EqualityType.GT.equals(query.getEqualityType())) {
			return accountRepository.findByBalanceGreaterThan(query.getBalance());
		}
		return accountRepository.findByBalanceLessThan(query.getBalance());
	}
}
