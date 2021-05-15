package com.astro.smitesolver.discord.repository;

import com.astro.smitesolver.discord.entity.totaldata.PickRateRankingHighMMR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HighMMRPickRateRepository extends JpaRepository<PickRateRankingHighMMR, Integer> {
}
