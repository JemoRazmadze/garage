package com.example.garage.controller;

import com.example.garage.dto.CarResponse;
import com.example.garage.request.CreateCarRequest;
import com.example.garage.request.UpdateCarRequest;
import com.example.garage.dto.PageResponse;
import com.example.garage.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequiredArgsConstructor
@RequestMapping("/car")
public class CarController {

    private static final Logger log = LoggerFactory.getLogger(CarController.class);

    private final CarService carService;

    @PostMapping("/create")
    public CarResponse createCar(@RequestBody CreateCarRequest request) {
        log.info("Received request to create car: {}", request);
        return carService.createCar(request);
    }

    @GetMapping("/find-car/{id}")
    public CarResponse getCarById(@PathVariable String id) {
        return carService.getCarById(id);
    }

    @GetMapping("/find-all-cars")
    public ResponseEntity<PageResponse> getCars(
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) Integer horsePower,
            @RequestParam(required = false) Integer carDisc,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponse cars = carService.getCars(model, color, price, horsePower,carDisc,page, size);
        return ResponseEntity.ok(cars);
    }


    @PutMapping("/update-car/{id}")
    public CarResponse updateCar(@PathVariable String id,
                                 @RequestBody UpdateCarRequest request) {
        return carService.updateCar(id, request);
    }

    @DeleteMapping("/delete-car/{id}")
    public void deleteCar(@PathVariable String id) {
        log.info("Received request to delete car with ID: {}", id);
        carService.deleteCar(id);
    }
}
