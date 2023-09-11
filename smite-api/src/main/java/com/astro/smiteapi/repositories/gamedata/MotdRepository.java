package com.astro.smiteapi.repositories.gamedata;

import com.astro.smiteapi.models.gamedata.matches.MotdInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotdRepository extends CrudRepository<MotdInfo, String> {
}
