package com.example.garage.mapper;

import com.example.garage.dto.CarResponse;
import com.example.garage.entity.Car;

import java.util.List;
import java.util.stream.Collectors;

public class CarMapper {
    private CarMapper() {}

    public static List<CarResponse> map(List<Car> cars) {
        return cars.stream()
                .map(CarMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    public static CarResponse mapToDTO(Car car) {
        CarResponse res = new CarResponse();
        res.setId(car.getId());
        res.setModel(car.getModel());
        res.setColor(car.getColor());
        res.setPrice(car.getPrice());
        res.setHorsepower(car.getHorsepower());
        return res;
    }
}