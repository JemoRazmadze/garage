package com.example.garage.repository;

import com.example.garage.entity.Car;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CarRepository extends MongoRepository<Car, String> {
    List<Car> findByColor(String color);
}
