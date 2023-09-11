package com.astro.smiteapi.repositories.player;

import com.astro.smiteapi.models.player.matches.PlayerMatchData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerMatchDataRepository extends CrudRepository<PlayerMatchData, Integer> {
}
