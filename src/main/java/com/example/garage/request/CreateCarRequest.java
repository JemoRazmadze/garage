package com.example.garage.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCarRequest{
    private String model;
    private String color;
    private Integer horsePower;
    private Double price;
}

