package com.example.garage.service;

import com.example.garage.dto.*;
import com.example.garage.entity.Car;
import com.example.garage.exceptions.ResourceNotFoundException;
import com.example.garage.mapper.CarMapper;
import com.example.garage.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public CarService(CarRepository carRepository, MongoTemplate mongoTemplate) {
        this.carRepository = carRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public CarResponse createCar(CreateCarRequest request) {
        Car car = new Car();
        car.setModel(request.getModel());
        car.setColor(request.getColor());
        car.setHorsepower(request.getHorsepower());
        car.setPrice(request.getPrice());
        return CarMapper.mapToDTO(carRepository.save(car));
    }

    public CarResponse getCarById(String id) {
        Car car = carRepository.findById(id).orElseThrow();
        return CarMapper.mapToDTO(car);
    }

    public Page<CarResponse> getAllCars(String color, String price, String model, int page, int size) {
        Query query = new Query();

        if (color != null && !color.isEmpty()) {
            query.addCriteria(Criteria.where("color").is(color));
        }
        if (price != null && !price.isEmpty()) {
            try {
                int intPrice = Integer.parseInt(price);
                query.addCriteria(Criteria.where("price").is(intPrice));
            } catch (NumberFormatException ignored) {}
        }
        if (model != null && !model.isEmpty()) {
            query.addCriteria(Criteria.where("model").is(model));
        }

        long total = mongoTemplate.count(query, Car.class);
        Pageable pageable = PageRequest.of(page, size);
        query.with(pageable);

        List<Car> cars = mongoTemplate.find(query, Car.class);
        List<CarResponse> carResponses = CarMapper.map(cars);

        return new PageImpl<>(carResponses, pageable, total);
    }

    public CarResponse updateCar(String id, UpdateCarRequest request) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
        car.setModel(request.getModel());
        car.setColor(request.getColor());
        car.setHorsepower(request.getHorsepower());
        car.setPrice(request.getPrice());
        return CarMapper.mapToDTO(carRepository.save(car));
    }

    public void deleteCar(String id) {
        carRepository.deleteById(id);
    }
}