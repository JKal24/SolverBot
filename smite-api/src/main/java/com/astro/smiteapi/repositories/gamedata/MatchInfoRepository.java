package com.astro.smiteapi.repositories.gamedata;

import com.astro.smiteapi.models.gamedata.matches.MatchInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchInfoRepository extends CrudRepository<MatchInfo, Integer> {
}
