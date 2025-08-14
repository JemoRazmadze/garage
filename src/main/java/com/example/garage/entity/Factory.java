package com.example.garage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Factory {
    private String id;
    private String factoryName;
    private String email;

    private List<EngineEntity> engines;
}
