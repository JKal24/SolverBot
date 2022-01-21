package com.astro.smitesolver.discord.repository;

import com.astro.smitesolver.discord.entity.totaldata.TotalGodDataHighMMR;
import org.springframework.stereotype.Repository;

@Repository
public interface HighMMRPerformanceRepository extends GodPerformanceRepository<TotalGodDataHighMMR, Integer> {

}
