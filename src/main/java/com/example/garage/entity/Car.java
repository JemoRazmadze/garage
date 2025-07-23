package com.example.garage.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.annotation.CreatedDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "cars")
public class Car {
    @Id
    private String id;
    private String model;
    private String color;
    private int horsepower;
    private double price;

    @CreatedDate
    private Long createdAt;
}

