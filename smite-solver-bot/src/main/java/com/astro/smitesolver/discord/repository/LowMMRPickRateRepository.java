package com.astro.smitesolver.discord.repository;

import com.astro.smitesolver.discord.entity.totaldata.PickRateRankingLowMMR;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LowMMRPickRateRepository extends JpaRepository<PickRateRankingLowMMR, Integer> {
}
