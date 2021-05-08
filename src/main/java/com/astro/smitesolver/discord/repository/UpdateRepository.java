package com.astro.smitesolver.discord.repository;

import com.astro.smitesolver.discord.entity.auxillary.UpdateData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface UpdateRepository extends JpaRepository<UpdateData, LocalDate> {
}
