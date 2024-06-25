package com.example.application.Repository;

import com.example.application.data.LeaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaderRepository extends JpaRepository<LeaderEntity, Long> {
}