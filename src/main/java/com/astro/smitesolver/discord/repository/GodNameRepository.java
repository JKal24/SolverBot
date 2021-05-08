package com.astro.smitesolver.discord.repository;

import com.astro.smitesolver.discord.entity.auxillary.GodName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GodNameRepository extends JpaRepository<GodName, Integer> {
    @Query(value = "SELECT * FROM god_names WHERE god_name=?1",nativeQuery = true)
    GodName findByName(String name);
}
