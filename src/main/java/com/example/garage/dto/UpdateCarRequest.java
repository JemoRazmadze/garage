package com.example.garage.dto;

import jakarta.validation.constraints.*;

public record UpdateCarRequest(
        @NotBlank String model,
        @NotBlank String color,
        @Min(1) int horsepower,
        @Positive double price
) {}
