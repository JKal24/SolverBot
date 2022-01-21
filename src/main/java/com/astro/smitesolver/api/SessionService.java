package com.astro.smitesolver.api;

import com.astro.smitesolver.smite.repository.SessionRepository;
import com.astro.smitesolver.smite.model.SessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    @Autowired
    private SessionRepository repository;

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
