package com.example.garage.dto.car;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCarRequest {
    String model;
    String color;
    Integer horsepower;
    Double price;
}


