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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private static final Logger log = LoggerFactory.getLogger(CarService.class);

    private final CarRepository carRepository;
    private final MongoTemplate mongoTemplate;

    @CacheEvict(value = "carList", allEntries = true)
    @CachePut(value = "cars", key = "#result.id")
    public CarResponse createCar(CreateCarRequest request) {
        log.info("Creating new car with model: {}", request.getModel());
        validation(request);
        Car car = new Car();
        car.setPrice(request.getPrice());
        car.setColor(request.getColor());
        car.setModel(request.getModel());
        car.setHorsePower(request.getHorsePower());
        car = carRepository.save(car);
        log.debug("Created car details: {}", car);
        return CarMapper.mapToDTO(car);
    }

    @Cacheable(value = "cars", key = "#id")
    public CarResponse getCarById(String id) {
        log.info("Fetching car with ID: {}", id);
        Car car = carRepository.findById(id)
                .orElseThrow(() -> {log.error("Car not found with ID: {}", id);
        return new ResourceNotFoundException("მანქანა ვერ მოიძებნა");});
        log.debug("Found car: {}", car);
        return CarMapper.mapToDTO(car);
    }

    @Cacheable(value = "carList", key = "T(java.util.Objects).hash(#model, #color, #price, #horsePower, #page, #size)")
    public PageResponse getCars(String model, String color, Double price, Integer horsePower, int page, int size) {
        log.info("Getting cars with filters - model: {}, color: {}, price: {}, horsePower: {}, page: {}, size: {}",
                model, color, price, horsePower, page, size);
        Query query = new Query();

        List<Criteria> filters = new ArrayList<>();
        if (model != null) filters.add(Criteria.where("model").is(model));
        if (color != null) filters.add(Criteria.where("color").is(color));
        if (price != null) filters.add(Criteria.where("price").is(price));
        if(horsePower != null) filters.add(Criteria.where("horsepower").gt(horsePower));
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

        log.debug("Found {} cars", response.size());

        return new PageResponse(response, pageable, total);
    }

    @CachePut(value = "cars", key = "#result.id")
    public CarResponse updateCar(String id, UpdateCarRequest request) {
        log.info("Updating car with ID: {}", id);
        Car car = carRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Cannot update: car not found with ID: {}", id);
                    return new ResourceNotFoundException("მანქანა ვერ მოიძებნა");});

        car.setModel(request.getModel());
        car.setColor(request.getColor());
        car.setHorsePower(request.getHorsePower());
        car.setPrice(request.getPrice());

        car = carRepository.save(car);
        log.info("Car updated: {}", car.getId());
        return CarMapper.mapToDTO(car);
    }

    @CacheEvict(value = {"cars", "carList"}, allEntries = true)
    public void deleteCar(String id) {
        log.info("Deleting car with ID: {}", id);
        if (!carRepository.existsById(id)) {
            log.warn("Tried to delete non-existing car with ID: {}", id);
            throw new ResourceNotFoundException(Constant.CAR_NOT_FOUND);
        }
        carRepository.deleteById(id);
        log.info("Car deleted with ID: {}", id);
    }


    public void validation(CreateCarRequest request){

        if (request.getModel() == null || request.getModel().isEmpty()) {
            log.error("Validation failed: model field is empty");
            throw new ResourceNotFoundException(Constant.MODEL_ERROR_MESSAGE);
        }

        if (request.getColor() == null || request.getColor().isEmpty()) {
            log.error("Validation failed: color field is empty");
            throw new ResourceNotFoundException(Constant.COLOR_ERROR_MESSAGE);
        }

        if(request.getHorsePower() == null || request.getHorsePower() <= 0) {
            log.error("Validation failed: horsepower invalid");
            throw new ResourceNotFoundException(Constant.HP_ERROR_MESSAGE);
        }

        if(request.getPrice() == null || request.getPrice() <= 0) {
            log.error("Validation failed: price invalid");
            throw new ResourceNotFoundException(Constant.PRICE_ERROR_MESSAGE);
        }

    }
}
