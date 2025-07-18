package com.example.garage.controller;

import com.example.garage.dto.CarResponse;
import com.example.garage.dto.CreateCarRequest;
import com.example.garage.dto.UpdateCarRequest;
import com.example.garage.service.CarService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/all-cars")
    public List<CarResponse> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/search/{id}")
    public CarResponse getCarById(@PathVariable String id) {
        return carService.getCarById(id);
    }

    @GetMapping("/search-color")
    public List<CarResponse> getCarsByColor(@RequestParam String color) {
        return carService.findByColor(color);
    }

    @PostMapping("/create")
    public CarResponse createCar(@Valid @RequestBody CreateCarRequest request) {
        return carService.createCar(request);
    }

    @PutMapping("/update/{id}")
    public CarResponse updateCar(@PathVariable String id, @Valid @RequestBody UpdateCarRequest request) {
        return carService.updateCar(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCar(@PathVariable String id) {
        carService.deleteCar(id);
    }
}
