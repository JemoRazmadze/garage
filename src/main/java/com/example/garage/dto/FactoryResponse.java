package com.example.garage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FactoryResponse {
    @JsonProperty("factory_id")
    private String id;
    @JsonProperty("factory_name")
    private String factoryName;
    @JsonProperty("factory_email")
    private String email;

    private List<EngineResponse> engines;
}
