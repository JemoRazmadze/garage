package com.example.garage.controller;

import com.example.garage.dto.*;
import com.example.garage.request.CreateCarRequest;
import com.example.garage.request.UpdateCarRequest;
import com.example.garage.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/car")
public class CarController {

    private final CarService carService;

    @PostMapping("/create")
    public CarResponse createCar(@RequestBody @Valid CreateCarRequest request) {
        return carService.createCar(request);
    }

    @GetMapping("/find-car/{id}")
    public CarResponse getCarById(@PathVariable String id) {
        return carService.getCarById(id);
    }

    @GetMapping("/find-all-cars")
    public Page<CarResponse> getCars(
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) Double price,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return carService.getCars(model, color, price, page, size);
    }

    @PutMapping("/update-car/{id}")
    public CarResponse updateCar(@PathVariable String id,
                                 @RequestBody @Valid UpdateCarRequest request) {
        return carService.updateCar(id, request);
    }

    @DeleteMapping("/delete-car/{id}")
    public void deleteCar(@PathVariable String id) {
        carService.deleteCar(id);
    }
}
