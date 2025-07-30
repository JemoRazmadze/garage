package com.example.garage.mapper;

import com.example.garage.dto.car.CarResponse;
import com.example.garage.entity.Car;

public class CarMapper {

    public static CarResponse mapToDTO(Car car) {

        CarResponse response = new CarResponse();

        response.setId(car.getId());
        response.setModel(car.getModel());
        response.setColor(car.getColor());
        response.setHorsePower(car.getHorsePower());
        response.setPrice(car.getPrice());
        response.setCreatedAt(car.getCreatedAt());

        return response;
    }
}
