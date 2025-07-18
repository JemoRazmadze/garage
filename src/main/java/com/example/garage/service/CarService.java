package com.example.garage.service;

import com.example.garage.dto.CarResponse;
import com.example.garage.dto.CreateCarRequest;
import com.example.garage.dto.UpdateCarRequest;
import com.example.garage.entity.Car;
import com.example.garage.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<CarResponse> getAllCars() {
        return carRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public CarResponse getCarById(String id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
        return mapToResponse(car);
    }

    public List<CarResponse> findByColor(String color) {
        return carRepository.findByColor(color)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public CarResponse createCar(CreateCarRequest request) {
        Car car = new Car(request.getModel(), request.getColor(), request.getHorsepower(), request.getPrice());
        Car saved = carRepository.save(car);
        return mapToResponse(saved);
    }

    public CarResponse updateCar(String id, UpdateCarRequest request) {
        Car existing = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));

        existing.setModel(request.getModel());
        existing.setColor(request.getColor());
        existing.setHorsepower(request.getHorsepower());
        existing.setPrice(request.getPrice());

        Car updated = carRepository.save(existing);
        return mapToResponse(updated);
    }

    public void deleteCar(String id) {
        carRepository.deleteById(id);
    }

    private CarResponse mapToResponse(Car car) {
        return new CarResponse(
                car.getId(),
                car.getModel(),
                car.getColor(),
                car.getHorsepower(),
                car.getPrice()
        );
    }
}
