package com.example.application.Repository;

import com.example.application.data.ControllerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ControllerRepository extends JpaRepository<ControllerEntity, Long> {
}
