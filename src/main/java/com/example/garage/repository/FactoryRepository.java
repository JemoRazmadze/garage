package com.example.garage.repository;

import com.example.garage.entity.Factory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactoryRepository extends MongoRepository<Factory, String> {
}
