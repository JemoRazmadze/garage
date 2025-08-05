package com.example.garage.request;

import lombok.Data;

@Data
public class CreateCarRequest{
    private String model;
    private String color;
    private Integer horsePower;
    private Double price;
}

