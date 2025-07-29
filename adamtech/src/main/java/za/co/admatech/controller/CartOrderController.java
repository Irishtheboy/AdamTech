/*
CartOrderController.java
Author: Naqeebah Khan (219099073)
Date: 03 June 2025 */
package za.co.admatech.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.domain.CartOrder;
import za.co.admatech.service.cartorder_domain_service.ICartOrderService;

import java.util.List;

@RestController
@RequestMapping("/cart-orders")
@CrossOrigin(origins = "*")
public class CartOrderController {
    private final ICartOrderService cartOrderService;

    public CartOrderController(ICartOrderService cartOrderService) {
        this.cartOrderService = cartOrderService;
    }

    @PostMapping
    public ResponseEntity<CartOrder> create(@Valid @RequestBody CartOrder cartOrder) {
        CartOrder created = cartOrderService.create(cartOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartOrder> read(@PathVariable String id) {
        CartOrder cartOrder = cartOrderService.read(id);
        return ResponseEntity.ok(cartOrder);
    }

    @PutMapping
    public ResponseEntity<CartOrder> update(@Valid @RequestBody CartOrder cartOrder) {
        CartOrder updated = cartOrderService.update(cartOrder);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean deleted = cartOrderService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CartOrder>> getAll() {
        return ResponseEntity.ok(cartOrderService.getAll());
    }
}