package com.astro.smitesolver.discord.repository;

import com.astro.smitesolver.discord.entity.totaldata.TotalGodDataLowMMR;
import org.springframework.stereotype.Repository;

@Repository
public interface LowMMRPerformanceRepository extends GodPerformanceRepository<TotalGodDataLowMMR, Integer>{
}
