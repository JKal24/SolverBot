package com.astro.smiteapi.repositories.session;

import com.astro.smiteapi.models.session.SessionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmiteSessionRepository extends JpaRepository<SessionInfo, Integer> {
}
