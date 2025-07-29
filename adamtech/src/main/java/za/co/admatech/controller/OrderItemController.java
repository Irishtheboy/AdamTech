/*
OrderItemController.java
Author: Naqeebah Khan (219099073)
Date: 03 June 2025 */
package za.co.admatech.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.domain.OrderItem;
import za.co.admatech.service.order_item_domain_service.OrderItemService;

import java.util.List;

@RestController
@RequestMapping("/order-items")
@CrossOrigin(origins = "*") // Adjust origins as needed for production
public class OrderItemController {
    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping
    public ResponseEntity<OrderItem> create(@Valid @RequestBody OrderItem orderItem) {
        OrderItem created = orderItemService.create(orderItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> read(@PathVariable String id) {
        OrderItem orderItem = orderItemService.read(id);
        return ResponseEntity.ok(orderItem);
    }

    @PutMapping
    public ResponseEntity<OrderItem> update(@Valid @RequestBody OrderItem orderItem) {
        OrderItem updated = orderItemService.update(orderItem);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean deleted = orderItemService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<OrderItem>> getAll() {
        return ResponseEntity.ok(orderItemService.getAll());
    }
}