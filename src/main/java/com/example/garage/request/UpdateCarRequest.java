package com.example.garage.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCarRequest {
    private String model;
    private String color;
    private Integer horsePower;
    private Double price;
}


