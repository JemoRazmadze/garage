package com.example.garage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarResponse {
    private String id;
    private String model;
    private String color;
    private int horsepower;
    private double price;
}
