package com.example.application.services;

import com.example.application.Repository.ExpertsRepository;
import com.example.application.data.ExpertsEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ExpertsService {
    private final ExpertsService expertsRepository;

    @Autowired
    public ExpertsService(ExpertsRepository expertsRepository) {
        this.expertsRepository = (ExpertsService) expertsRepository;
    }

    public List<ExpertsEntity> findAll() {
        return expertsRepository.findAll();
    }

    public ExpertsEntity save(ExpertsEntity expertsEntity) {
        return expertsRepository.save(expertsEntity);
    }

    public void delete(ExpertsEntity expertsEntity) {
        expertsRepository.delete(expertsEntity);
    }
}
