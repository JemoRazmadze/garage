package com.example.garage.mapper;

import com.example.garage.dto.CarResponse;
import com.example.garage.entity.Car;

public class CarMapper {
    public static CarResponse mapToDTO(Car car) {
        return CarResponse.builder()
                .id(car.getId())
                .model(car.getModel())
                .color(car.getColor())
                .horsepower(car.getHorsepower())
                .price(car.getPrice())
                .createdAt(car.getCreatedAt())
                .build();
    }
}
