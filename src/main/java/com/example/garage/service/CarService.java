package com.example.garage.service;

import com.example.garage.util.Constant;
import com.example.garage.dto.CarResponse;
import com.example.garage.dto.PageResponse;
import com.example.garage.entity.Car;
import com.example.garage.exceptions.ResourceNotFoundException;
import com.example.garage.mapper.CarMapper;
import com.example.garage.repository.CarRepository;
import com.example.garage.request.CreateCarRequest;
import com.example.garage.request.UpdateCarRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final MongoTemplate mongoTemplate;

    public CarResponse createCar(CreateCarRequest request) {
        validation(request);
        Car car = new Car();
        car.setPrice(request.getPrice());
        car.setColor(request.getColor());
        car.setModel(request.getModel());
        car.setHorsePower(request.getHorsePower());
        car = carRepository.save(car);
        return CarMapper.mapToDTO(car);
    }

    public CarResponse getCarById(String id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("მანქანა ვერ მოიძებნა"));
        return CarMapper.mapToDTO(car);
    }

    public PageResponse getCars(String model, String color, Double price, Integer horsePower, int page, int size) {
        Query query = new Query();

        List<Criteria> filters = new ArrayList<>();
        if (model != null) filters.add(Criteria.where("model").is(model));
        if (color != null) filters.add(Criteria.where("color").is(color));
        if (price != null) filters.add(Criteria.where("price").is(price));
        if(price != null) filters.add(Criteria.where("horsepower").gt(horsePower));
        if (!filters.isEmpty()) query.addCriteria(new Criteria().andOperator(filters.toArray(new Criteria[0])));


        Sort sort = Sort.by(Sort.Direction.DESC, "created_at");

        Pageable pageable = PageRequest.of(page, size, sort);
        query.with(pageable);

        List<Car> cars = mongoTemplate.find(query, Car.class);

        Query countQuery = Query.of(query).limit(-1).skip(-1);
        long total = mongoTemplate.count(countQuery, Car.class);

        List<CarResponse> response = cars.stream()
                .map(CarMapper::mapToDTO)
                .toList();

        return new PageResponse(response, pageable, total);
    }

    public CarResponse updateCar(String id, UpdateCarRequest request) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("მანქანა ვერ მოიძებნა"));

        car.setModel(request.getModel());
        car.setColor(request.getColor());
        car.setHorsePower(request.getHorsePower());
        car.setPrice(request.getPrice());

        car = carRepository.save(car);
        return CarMapper.mapToDTO(car);
    }

    public void deleteCar(String id) {
        if (!carRepository.existsById(id)) {
            throw new ResourceNotFoundException(Constant.CAR_NOT_FOUND);
        }
        carRepository.deleteById(id);
    }


    public void validation(CreateCarRequest request){

        if (request.getModel() == null || request.getModel().isEmpty())
            throw new ResourceNotFoundException(Constant.MODEL_ERROR_MESSAGE);

        if (request.getColor() == null || request.getColor().isEmpty())
            throw new ResourceNotFoundException(Constant.COLOR_ERROR_MESSAGE);

        if(request.getHorsePower() == null || request.getHorsePower() <= 0)
            throw new ResourceNotFoundException(Constant.HP_ERROR_MESSAGE);

        if(request.getPrice() == null || request.getPrice() <= 0)
            throw new ResourceNotFoundException(Constant.PRICE_ERROR_MESSAGE);

    }
}
