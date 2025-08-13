package com.example.garage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarResponse {
    private String id;
    private String model;
    private String color;

    @JsonProperty("horse_power")
    private int horsePower;

    private double price;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("car_details")
    private CarDetailsResponse details;
}

