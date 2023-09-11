package com.astro.smiteapi.repositories.player;

import com.astro.smiteapi.models.player.PlayerStatistics;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerStatisticsRepository extends CrudRepository<PlayerStatistics, Integer> {
}
