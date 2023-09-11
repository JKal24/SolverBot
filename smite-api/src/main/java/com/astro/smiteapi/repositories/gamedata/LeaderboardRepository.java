package com.astro.smiteapi.repositories.gamedata;

import com.astro.smiteapi.models.characters.auxiliary.LeaderboardInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaderboardRepository extends CrudRepository<LeaderboardInfo, Integer> {
}
