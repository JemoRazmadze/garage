package com.example.garage.dto.car;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCarRequest{
    private String model;
    private String color;
    Integer horsePower;
    Double price;
}

