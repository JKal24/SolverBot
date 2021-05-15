package com.astro.smitesolver.discord.repository;

import com.astro.smitesolver.discord.entity.auxillary.BaseItemData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDataRepository extends JpaRepository<BaseItemData, Integer> {
}
