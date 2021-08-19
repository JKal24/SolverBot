package com.astro.smitesolver.discord.repository;

import com.astro.smitesolver.discord.entity.auxillary.GodName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GodNameRepository extends JpaRepository<GodName, Integer> {
    @Query(value = "SELECT g FROM god_names g WHERE g.godName=?1")
    GodName findByName(String name);
}
