package com.example.garage.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EngineEntity {
    private int resource;
    private String oil;
    private Double power;
}
