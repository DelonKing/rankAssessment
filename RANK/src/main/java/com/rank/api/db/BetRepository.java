package com.rank.api.db;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface BetRepository extends CrudRepository<Bet, Long> {

	
	public Optional<Bet> findByTransactionId(Long transactionID);

	public List<Bet> findTop10ByPlayerOrderByIdDesc(Player player);
}
