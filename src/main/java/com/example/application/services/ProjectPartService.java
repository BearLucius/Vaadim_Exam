package com.example.application.services;

import com.example.application.Repository.ProjectPartRepository;
import com.example.application.data.ProjectPartEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectPartService {

    private final ProjectPartRepository projectPartRepository;

    @Autowired
    public ProjectPartService(ProjectPartRepository projectPartRepository) {
        this.projectPartRepository = projectPartRepository;
    }

    public List<ProjectPartEntity> findAll() {
        return projectPartRepository.findAll();
    }

    public ProjectPartEntity save(ProjectPartEntity projectPartEntity) {
        return projectPartRepository.save(projectPartEntity);
    }

    public void delete(ProjectPartEntity projectPartEntity) {
        projectPartRepository.delete(projectPartEntity);
    }
}