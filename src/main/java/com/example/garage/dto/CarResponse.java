package com.example.garage.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarResponse {
    private String id;
    private String model;
    private String color;
    private int horsepower;
    private double price;
    private Long createdAt;
}
