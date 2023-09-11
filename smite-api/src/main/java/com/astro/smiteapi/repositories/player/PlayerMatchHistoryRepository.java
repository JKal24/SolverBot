package com.astro.smiteapi.repositories.player;

import com.astro.smiteapi.models.player.matches.PlayerMatchHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerMatchHistoryRepository extends CrudRepository<PlayerMatchHistory, Integer> {
}
