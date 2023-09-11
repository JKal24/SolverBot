package com.astro.smiteapi.repositories.player;

import com.astro.smiteapi.models.player.PlayerStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerStatusRepository extends CrudRepository<PlayerStatus, Integer> {
}
