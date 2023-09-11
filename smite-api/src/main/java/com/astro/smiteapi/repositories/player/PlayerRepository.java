package com.astro.smiteapi.repositories.player;

import com.astro.smiteapi.models.player.PlayerInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<PlayerInfo, Integer> {
}
