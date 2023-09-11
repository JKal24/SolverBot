package com.astro.smitesolver.discord.repository;

import com.astro.smitesolver.discord.entity.auxillary.BaseItemName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemNameRepository extends JpaRepository<BaseItemName, Integer> {
}
