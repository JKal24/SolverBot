package com.astro.smitesolver.discord.repository;

import com.astro.smitesolver.discord.entity.totaldata.WinRateRankingLowMMR;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LowMMRWinRateRepository extends JpaRepository<WinRateRankingLowMMR, Integer> {
}
