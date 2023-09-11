package com.astro.smiteapi.repositories.player;

import com.astro.smiteapi.models.player.auxiliary.SearchedPlayer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchedPlayerRepository extends CrudRepository<SearchedPlayer, Integer> {
}
