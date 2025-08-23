/*
CustomerController.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025 */
package za.co.admatech.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.domain.Customer;

import za.co.admatech.service.CustomerService;
@CrossOrigin(origins = "http://localhost:3000")

import za.co.admatech.service.customer_domain_service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "*") // Adjust origins as needed for production
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Customer> create(@Valid @RequestBody Customer customer) {
        Customer created = customerService.create(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> read(@PathVariable String id) {
        Customer customer = customerService.read(id);
        return ResponseEntity.ok(customer);
    }

    @PutMapping
    public ResponseEntity<Customer> update(@Valid @RequestBody Customer customer) {
        Customer updated = customerService.update(customer);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean deleted = customerService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAll() {
        return ResponseEntity.ok(customerService.getAll());
    }
}