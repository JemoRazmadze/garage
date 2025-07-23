package com.example.garage.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cars")
@Data
@Builder
public class Car {
    @Id
    @Field("id")
    private String id;
    @Field("model")
    private String model;
    @Field("color")
    private String color;
    @Field("horse_power")
    private int horsepower;
    @Field("price")
    private double price;
    @Field("created_at")
    private Long createdAt;
}

