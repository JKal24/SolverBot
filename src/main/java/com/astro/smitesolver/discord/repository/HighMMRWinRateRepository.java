package com.astro.smitesolver.discord.repository;

import com.astro.smitesolver.discord.entity.totaldata.WinRateRankingHighMMR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HighMMRWinRateRepository extends JpaRepository<WinRateRankingHighMMR, Integer> {
}
