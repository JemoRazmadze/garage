package com.example.garage.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCarRequest {
    @NotBlank
    private String model;

    @NotBlank
    private String color;

    @Positive
    private int horsepower;

    @Min(0)
    private double price;
}
