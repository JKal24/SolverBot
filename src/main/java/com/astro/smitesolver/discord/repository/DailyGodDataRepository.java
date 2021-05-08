package com.astro.smitesolver.discord.repository;

import com.astro.smitesolver.discord.entity.dailydata.DailyGodData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface DailyGodDataRepository<T extends DailyGodData, ID extends Serializable> extends JpaRepository<T, ID> {

}
