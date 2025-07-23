package com.example.garage.request;

import jakarta.validation.constraints.*;

public record CreateCarRequest(
        @NotBlank String model,
        @NotBlank String color,
        @Min(1) int horsepower,
        @Positive double price
) {}
