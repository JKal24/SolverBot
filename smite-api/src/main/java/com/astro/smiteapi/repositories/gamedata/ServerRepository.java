package com.astro.smiteapi.repositories.gamedata;

import com.astro.smiteapi.models.gamedata.ServerInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerRepository extends CrudRepository<ServerInfo, Integer> {
}
