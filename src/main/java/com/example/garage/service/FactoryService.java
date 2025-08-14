package com.example.garage.service;

import com.example.garage.dto.FactoryResponse;
import com.example.garage.entity.Factory;
import com.example.garage.exceptions.ResourceNotFoundException;
import com.example.garage.mapper.FactoryMapper;
import com.example.garage.repository.FactoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FactoryService {

    private final FactoryRepository repository;

    public FactoryService(FactoryRepository repository) {
        this.repository = repository;
    }

    public FactoryResponse createFactory(FactoryResponse response) {
        Factory factory = FactoryMapper.toEntity(response);
        Factory saved = repository.save(factory);
        return FactoryMapper.toDTO(saved);
    }

    public FactoryResponse getFactoryById(String id) {
        return repository.findById(id)
                .map(FactoryMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Factory not found"));
    }

    public List<FactoryResponse> getAllFactories() {
        return FactoryMapper.toDTOList(repository.findAll());
    }

    public FactoryResponse updateFactory(String id, FactoryResponse response) {
        Factory existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Factory not found"));
        existing.setFactoryName(response.getFactoryName());
        existing.setEmail(response.getEmail());
        existing.setEngines(FactoryMapper.toEntity(response).getEngines());
        return FactoryMapper.toDTO(repository.save(existing));
    }

    public void deleteFactory(String id) {
        repository.deleteById(id);
    }
}
