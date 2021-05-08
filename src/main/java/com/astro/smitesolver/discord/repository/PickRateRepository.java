package com.astro.smitesolver.discord.repository;

import com.astro.smitesolver.discord.entity.totaldata.PickRateRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PickRateRepository extends JpaRepository<PickRateRanking, Integer> {
}
