package com.example.garage.controller;

import com.example.garage.dto.*;
import com.example.garage.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    @PostMapping("/create")
    public CarResponse createCar(@RequestBody @Valid CreateCarRequest request) {
        return carService.createCar(request);
    }

    @GetMapping("/search/{id}")
    public CarResponse getCarById(@PathVariable String id) {
        return carService.getCarById(id);
    }

    @GetMapping
    public ResponseEntity<Page<CarResponse>> getCarByParam(@RequestParam(required = false) String color,
                                                           @RequestParam(required = false) String price,
                                                           @RequestParam(required = false) String model,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        Page<CarResponse> getCarsByParam = carService.getAllCars(color, price, model, page, size);
        return ResponseEntity.ok(getCarsByParam);
    }

    @PutMapping("/update/{id}")
    public CarResponse updateCar(@PathVariable String id, @RequestBody @Valid UpdateCarRequest request) {
        return carService.updateCar(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCar(@PathVariable String id) {
        carService.deleteCar(id);
    }
}