package com.example.application.Repository;

import com.example.application.data.ExpertsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpertsRepository extends JpaRepository<ExpertsEntity, Long> {
}
