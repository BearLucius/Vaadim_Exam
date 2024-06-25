package com.example.application.services;

import com.example.application.Repository.ContractRepository;
import com.example.application.data.ContractEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ContractService {

    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public List<ContractEntity> findAll() {
        return contractRepository.findAll();
    }

    public ContractEntity save(ContractEntity contractEntity) {
        return contractRepository.save(contractEntity);
    }

    public void delete(ContractEntity contractEntity) {
        contractRepository.delete(contractEntity);
    }
}
