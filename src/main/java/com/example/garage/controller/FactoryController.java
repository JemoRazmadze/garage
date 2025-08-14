package com.example.garage.controller;

import com.example.garage.dto.FactoryResponse;
import com.example.garage.service.FactoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/factories")
public class FactoryController {

    private final FactoryService service;

    public FactoryController(FactoryService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public FactoryResponse create(@RequestBody FactoryResponse response) {
        return service.createFactory(response);
    }

    @GetMapping("/find-by/{id}")
    public FactoryResponse getById(@PathVariable String id) {
        return service.getFactoryById(id);
    }

    @GetMapping("/find-all")
    public List<FactoryResponse> getAll() {
        return service.getAllFactories();
    }

    @PutMapping("update-by/{id}")
    public FactoryResponse update(@PathVariable String id, @RequestBody FactoryResponse response) {
        return service.updateFactory(id, response);
    }

    @DeleteMapping("delete-by/{id}")
    public void delete(@PathVariable String id) {
        service.deleteFactory(id);
    }
}
