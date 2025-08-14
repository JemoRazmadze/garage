package com.example.garage.mapper;

import com.example.garage.dto.EngineResponse;
import com.example.garage.dto.FactoryResponse;
import com.example.garage.entity.EngineEntity;
import com.example.garage.entity.Factory;

import java.util.List;
import java.util.stream.Collectors;

public class FactoryMapper {

    public static FactoryResponse toDTO(Factory factory) {
        if (factory == null) return null;
        return new FactoryResponse(
                factory.getId(),
                factory.getFactoryName(),
                factory.getEmail(),
                factory.getEngines().stream()
                        .map(e -> new EngineResponse(e.getResource(), e.getOil(), e.getPower()))
                        .collect(Collectors.toList())
        );
    }

    public static Factory toEntity(FactoryResponse response) {
        if (response == null) return null;
        return new Factory(
                response.getId(),
                response.getFactoryName(),
                response.getEmail(),
                response.getEngines().stream()
                        .map(e -> new EngineEntity(e.getResource(), e.getOil(), e.getPower()))
                        .collect(Collectors.toList())
        );
    }

    public static List<FactoryResponse> toDTOList(List<Factory> factories) {
        return factories.stream().map(FactoryMapper::toDTO).collect(Collectors.toList());
    }
}
