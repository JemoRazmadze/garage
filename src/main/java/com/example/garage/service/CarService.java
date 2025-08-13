package com.example.garage.service;

import com.example.garage.dto.CarResponse;
import com.example.garage.dto.PageResponse;
import com.example.garage.entity.Car;
import com.example.garage.entity.CarDetails;
import com.example.garage.exceptions.ResourceNotFoundException;
import com.example.garage.mapper.CarMapper;
import com.example.garage.repository.CarRepository;
import com.example.garage.request.CreateCarRequest;
import com.example.garage.request.UpdateCarRequest;
import com.example.garage.util.Constant;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {

    private static final Logger log = LoggerFactory.getLogger(CarService.class);

    private final CarRepository carRepository;
    private final MongoTemplate mongoTemplate;

    @CacheEvict(value = "carsByFilter", allEntries = true)
    @CachePut(value = "cars", key = "#result.id")
    public CarResponse createCar(CreateCarRequest request) {
        CarDetails carDetails = null;
        if (request.getCarDetails() != null) {
            carDetails = new CarDetails();
            carDetails.setCarDisc(request.getCarDetails().getCarDisc());
            carDetails.setCarSetting(request.getCarDetails().getCarSetting());
        }

        Car car = new Car();
        car.setModel(request.getModel());
        car.setColor(request.getColor());
        car.setHorsePower(request.getHorsePower());
        car.setPrice(request.getPrice());
        car.setCarDetails(carDetails);
        car.setCreatedAt(System.currentTimeMillis());

        car = carRepository.save(car);

        return CarMapper.mapToDTO(car);
    }

    @Cacheable(value = "cars", key = "#id")
    public CarResponse getCarById(String id) {
        log.info("Fetching car with ID: {}", id);
        Car car = carRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Car not found with ID: {}", id);
                    return new ResourceNotFoundException(Constant.CAR_NOT_FOUND);
                });
        return CarMapper.mapToDTO(car);
    }

    @Cacheable(value = "carsByFilter", key = "T(java.util.Objects).hash(#model, #color, #price, #horsePower, #page, #size)")
    public PageResponse getCars(String model, String color, Double price, Integer horsePower,Integer carDisc, int page, int size)
    {
        log.info("Fetching cars with filters - model: {}, color: {}, price: {}, horsePower: {}, page: {}, size: {}",
                model, color, price, horsePower, page, size);

        Query query = new Query();
        List<Criteria> filters = new ArrayList<>();

        if (model != null) filters.add(Criteria.where("model").is(model));
        if (color != null) filters.add(Criteria.where("color").is(color));
        if (price != null) filters.add(Criteria.where("price").is(price));
        if (horsePower != null) filters.add(Criteria.where("horsepower").gt(horsePower));
        if (carDisc != null) filters.add(Criteria.where("carDisc").is(carDisc));


        if (!filters.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(filters.toArray(new Criteria[0])));
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "created_at"));
        query.with(pageable);

        List<Car> cars = mongoTemplate.find(query, Car.class);

        Query countQuery = Query.of(query).limit(-1).skip(-1);
        long total = mongoTemplate.count(countQuery, Car.class);

        Map<String, CarResponse> responseMap = cars.stream()
                .map(CarMapper::mapToDTO)
                .collect(Collectors.toMap(
                        CarResponse::getId,
                        carResponse -> carResponse
                ));

        log.debug("Found {} cars", responseMap.size());

        return new PageResponse(responseMap, pageable, total);
    }

    @CacheEvict(value = "carsByFilter", allEntries = true)
    @CachePut(value = "cars", key = "#result.id")
    public CarResponse updateCar(String id, UpdateCarRequest request) {
        log.info("Updating car with ID: {}", id);
        Car car = carRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Cannot update: car not found with ID: {}", id);
                    return new ResourceNotFoundException(Constant.CAR_NOT_FOUND);
                });

        car.setModel(request.getModel());
        car.setColor(request.getColor());
        car.setHorsePower(request.getHorsePower());
        car.setPrice(request.getPrice());
        car.setCreatedAt(System.currentTimeMillis());
        car = carRepository.save(car);

        log.debug("Car updated: {}", car);

        return CarMapper.mapToDTO(car);
    }

    @CacheEvict(value = {"cars", "carsByFilter"}, allEntries = true)
    public void deleteCar(String id) {
        log.info("Deleting car with ID: {}", id);
        if (!carRepository.existsById(id)) {
            log.warn("Tried to delete non-existing car with ID: {}", id);
            throw new ResourceNotFoundException(Constant.CAR_NOT_FOUND);
        }
        carRepository.deleteById(id);
        log.info("Car deleted with ID: {}", id);
    }

    private void validate(CreateCarRequest request) {
        if (request.getModel() == null || request.getModel().isEmpty()) {
            throw new ResourceNotFoundException(Constant.MODEL_ERROR_MESSAGE);
        }
        if (request.getColor() == null || request.getColor().isEmpty()) {
            throw new ResourceNotFoundException(Constant.COLOR_ERROR_MESSAGE);
        }
        if (request.getHorsePower() == null || request.getHorsePower() <= 0) {
            throw new ResourceNotFoundException(Constant.HP_ERROR_MESSAGE);
        }
        if (request.getPrice() == null || request.getPrice() <= 0) {
            throw new ResourceNotFoundException(Constant.PRICE_ERROR_MESSAGE);
        }
    }
}
