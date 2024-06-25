package com.example.application.services;

import com.example.application.Repository.TeamRepository;
import com.example.application.data.TeamEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<TeamEntity> findAll() {
        return teamRepository.findAll();
    }

    public TeamEntity save(TeamEntity teamEntity) {
        return teamRepository.save(teamEntity);
    }

    public void delete(TeamEntity teamEntity) {
        teamRepository.delete(teamEntity);
    }
}