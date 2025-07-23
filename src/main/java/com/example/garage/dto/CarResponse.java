package com.example.garage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarResponse {
    @JsonProperty("id")
    private String id;
    @JsonProperty("model")
    private String model;
    @JsonProperty("color")
    private String color;
    @JsonProperty("horse_power")
    private int horsePower;
    @JsonProperty("price")
    private double price;
    @JsonProperty("created_at")
    private Long createdAt;
}
