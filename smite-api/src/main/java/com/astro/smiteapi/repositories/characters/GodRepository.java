package com.astro.smiteapi.repositories.characters;

import com.astro.smiteapi.models.characters.GodInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GodRepository extends CrudRepository<GodInfo, Integer> {
}
