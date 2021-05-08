package com.astro.smitesolver.discord.repository;

import com.astro.smitesolver.discord.entity.totaldata.WinRateRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WinRateRepository extends JpaRepository<WinRateRanking, Integer> {
}
