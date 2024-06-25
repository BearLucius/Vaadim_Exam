package com.example.application.services;

import com.example.application.Repository.LeaderRepository;
import com.example.application.data.LeaderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaderService {

    private final LeaderRepository leaderRepository;

    @Autowired
    public LeaderService(LeaderRepository leaderRepository) {
        this.leaderRepository = leaderRepository;
    }

    public List<LeaderEntity> findAll() {
        return leaderRepository.findAll();
    }

    public LeaderEntity save(LeaderEntity leaderEntity) {
        return leaderRepository.save(leaderEntity);
    }

    public void delete(LeaderEntity leaderEntity) {
        leaderRepository.delete(leaderEntity);
    }
}