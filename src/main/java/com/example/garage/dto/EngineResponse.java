package com.example.garage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EngineResponse {
    @JsonProperty("engine_resource")
    private int resource;
    @JsonProperty("engine_oil")
    private String oil;
    @JsonProperty("engine_power")
    private Double power;
}
