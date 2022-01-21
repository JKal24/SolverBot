package com.astro.smitesolver.smite.repository;

import com.astro.smitesolver.smite.model.SessionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<SessionInfo, Integer> {
}
