package com.astro.smitesolver.discord.repository;

import com.astro.smitesolver.discord.entity.totaldata.BanRateRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BanRateRepository extends JpaRepository<BanRateRanking, Integer> {
}
