package com.example.application.Repository;


import com.example.application.data.SamplePersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface SamplePersonRepository
        extends
        JpaRepository<SamplePersonEntity, Long>,
        JpaSpecificationExecutor<SamplePersonEntity> {


}
