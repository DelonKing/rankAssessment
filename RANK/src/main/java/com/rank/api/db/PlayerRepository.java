package com.rank.api.db;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Long> {

	Optional<Player> findByUserName(String playerUsername);
}
