package com.astro.smitesolver.discord.repository;

import com.astro.smitesolver.discord.entity.totaldata.TotalGodData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface GodPerformanceRepository<T extends TotalGodData, ID extends Serializable> extends JpaRepository<T, ID> {
}
