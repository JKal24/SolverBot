package com.astro.smitesolver.discord.repository;

import com.astro.smitesolver.discord.entity.totaldata.MatchRecordedData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface RecordedMatchRepository extends JpaRepository<MatchRecordedData, LocalDate> {
}
