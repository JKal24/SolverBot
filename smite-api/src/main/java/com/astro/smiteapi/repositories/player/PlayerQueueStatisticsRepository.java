package com.astro.smiteapi.repositories.player;

import com.astro.smiteapi.models.player.matches.PlayerQueueStatistics;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerQueueStatisticsRepository extends CrudRepository<PlayerQueueStatistics, Integer> {
}
