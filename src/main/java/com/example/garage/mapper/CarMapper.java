package com.example.garage.mapper;

import com.example.garage.dto.CarDetailsResponse;
import com.example.garage.dto.CarResponse;
import com.example.garage.entity.Car;
import com.example.garage.entity.CarDetails;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class CarMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault());

    public static CarResponse mapToDTO(Car car) {
        CarResponse response = new CarResponse();

        response.setId(car.getId());
        response.setModel(car.getModel());
        response.setColor(car.getColor());
        response.setHorsePower(car.getHorsePower());
        response.setPrice(car.getPrice());
        response.setCreatedAt(car.getCreatedAt() != null
                ? formatter.format(Instant.ofEpochMilli(car.getCreatedAt()))
                : null);

        response.setDetails(mapCarDetails(car.getCarDetails()));

        return response;
    }

    public static CarDetailsResponse mapCarDetails(CarDetails carDetails) {
        if (carDetails == null) return null;

        CarDetailsResponse detailsResponse = new CarDetailsResponse();
        detailsResponse.setCarDisc(carDetails.getCarDisc());
        detailsResponse.setCarSetting(carDetails.getCarSetting());

        return detailsResponse;
    }


}
