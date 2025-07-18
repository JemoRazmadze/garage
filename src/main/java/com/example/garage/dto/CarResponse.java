package com.example.garage.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CarResponse {
    private String id;
    private String model;
    private String color;
    private int horsepower;
    private double price;

    public CarResponse() {}

    public CarResponse(String id, String model, String color, int horsepower, double price) {
        this.id = id;
        this.model = model;
        this.color = color;
        this.horsepower = horsepower;
        this.price = price;
    }

}
