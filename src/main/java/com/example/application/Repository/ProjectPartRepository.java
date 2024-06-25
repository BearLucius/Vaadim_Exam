package com.example.application.Repository;

import com.example.application.data.ProjectPartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectPartRepository extends JpaRepository<ProjectPartEntity, Long> {
}