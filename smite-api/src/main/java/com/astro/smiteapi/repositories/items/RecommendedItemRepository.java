package com.astro.smiteapi.repositories.items;

import com.astro.smiteapi.models.items.RecommendedItemInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendedItemRepository extends CrudRepository<RecommendedItemInfo, Integer> {
}
