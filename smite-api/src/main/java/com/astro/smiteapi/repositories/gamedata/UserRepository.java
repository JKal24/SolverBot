package com.astro.smiteapi.repositories.gamedata;

import com.astro.smiteapi.models.gamedata.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserInfo, Integer> {
}
