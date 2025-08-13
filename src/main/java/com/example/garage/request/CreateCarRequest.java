package com.example.garage.request;

import lombok.Data;

@Data
public class CreateCarRequest {
    private String model;
    private String color;
    private Integer horsePower;
    private Double price;

    private CarDetailsRequest carDetails;

    @Data
    public static class CarDetailsRequest {
        private String carDisc;
        private String carSetting;
    }
}

