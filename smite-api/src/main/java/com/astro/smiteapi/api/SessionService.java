package com.astro.smiteapi.api;

import com.astro.smiteapi.models.session.SessionInfo;
import com.astro.smiteapi.repositories.session.SmiteSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    @Autowired
    private SmiteSessionRepository repository;

    public void addConnection(SessionInfo info) {
        repository.save(info);
    }

    public void addConnections(SessionInfo[] sessionInfo) {
        for (SessionInfo info : sessionInfo) {
            addConnection(info);
        }
    }

    public Iterable<SessionInfo> getConnections() {
        return repository.findAll();
    }

    public void deleteConnection(SessionInfo info) { repository.delete(info); }
}
