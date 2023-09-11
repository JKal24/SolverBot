package com.astro.smiteapi.repositories.player;

import com.astro.smiteapi.models.player.matches.PlayerLiveMatchData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerLiveMatchDataRepository extends CrudRepository<PlayerLiveMatchData, Integer> {
}
