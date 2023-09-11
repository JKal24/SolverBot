package com.astro.smiteapi.repositories.player;

import com.astro.smiteapi.models.player.PlayerID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerIDRepository extends CrudRepository<PlayerID, Integer> {
}
