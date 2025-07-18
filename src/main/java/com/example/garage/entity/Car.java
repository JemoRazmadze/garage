package com.example.garage.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "cars")
public class Car {
    @Id
    private String id;

    private String model;
    private String color;
    private int horsepower;
    private double price;
    private long createdAt;

    public Car(String model, String color, int horsepower, double price) {
        this.model = model;
        this.color = color;
        this.horsepower = horsepower;
        this.price = price;
        this.createdAt = System.currentTimeMillis();
    }
}
