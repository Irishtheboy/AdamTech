/*
OrderController.java
Author: Naqeebah Khan (219099073)
Date: 03 June 2025 */
package za.co.admatech.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.domain.Order;
import za.co.admatech.service.order_domain_service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*") // Adjust origins as needed for production
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> create(@Valid @RequestBody Order order) {
        Order created = orderService.create(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> read(@PathVariable String id) {
        Order order = orderService.read(id);
        return ResponseEntity.ok(order);
    }

    @PutMapping
    public ResponseEntity<Order> update(@Valid @RequestBody Order order) {
        Order updated = orderService.update(order);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean deleted = orderService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }
}