package com.astro.smitesolver.discord.repository;

import com.astro.smitesolver.discord.entity.totaldata.BanRateRankingLowMMR;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LowMMRBanRateRepository extends JpaRepository<BanRateRankingLowMMR, Integer> {
}
