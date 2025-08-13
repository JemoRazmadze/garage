package com.example.garage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarDetailsResponse {
    @JsonProperty("car_disc")
    private String carDisc;

    @JsonProperty("car_setting")
    private String carSetting;
}
