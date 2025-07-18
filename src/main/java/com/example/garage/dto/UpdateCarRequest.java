package com.example.garage.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateCarRequest {
    @NotBlank(message = "Model is mandatory")
    private String model;

    @NotBlank(message = "Color is mandatory")
    private String color;

    @Positive(message = "Horsepower must be positive")
    private int horsepower;

    @Min(value = 0, message = "Price must be zero or positive")
    private double price;

}
