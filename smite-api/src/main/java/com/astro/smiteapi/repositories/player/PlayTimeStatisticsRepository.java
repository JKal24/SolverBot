package com.astro.smiteapi.repositories.player;

import com.astro.smiteapi.models.player.matches.PlayTimeStatistics;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayTimeStatisticsRepository extends CrudRepository<PlayTimeStatistics, Integer> {
}
