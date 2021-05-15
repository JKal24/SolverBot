package com.astro.smitesolver.discord.repository;

import com.astro.smitesolver.discord.entity.totaldata.BanRateRankingHighMMR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HighMMRBanRateRepository extends JpaRepository<BanRateRankingHighMMR, Integer> {
}
