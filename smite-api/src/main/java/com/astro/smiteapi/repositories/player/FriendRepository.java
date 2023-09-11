package com.astro.smiteapi.repositories.player;

import com.astro.smiteapi.models.player.auxiliary.FriendsInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends CrudRepository<FriendsInfo, Integer> {
}
