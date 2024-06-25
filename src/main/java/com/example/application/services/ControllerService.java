package com.example.application.services;

import com.example.application.Repository.ControllerRepository;
import com.example.application.data.ControllerEntity;
import com.example.application.data.LeaderEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ControllerService {
    private final ControllerRepository controllerRepository;

    @Autowired
    public ControllerService(ControllerRepository controllerRepository) {
        this.controllerRepository = controllerRepository;
    }

    public List<ControllerEntity> findAll() {
        return controllerRepository.findAll();
    }

    public ControllerEntity save(ControllerEntity controllerEntity) {
        return controllerRepository.save(controllerEntity);
    }

    public void delete(ControllerEntity controllerEntity) {
        controllerRepository.delete(controllerEntity);
    }
}
