package com.astro.smiteapi.repositories.items;

import com.astro.smiteapi.models.items.BaseItemInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseItemRepository extends CrudRepository<BaseItemInfo, Integer> {
}
