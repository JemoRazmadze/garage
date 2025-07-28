package com.example.garage.request;

public record UpdateCarRequest(
        String model,
        String color,
        Integer horsepower,
        Double price
) {}

