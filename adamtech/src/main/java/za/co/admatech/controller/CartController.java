/*
CartController.java
Author: Naqeebah Khan (219099073)
Date: 03 June 2025 */
package za.co.admatech.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.domain.Cart;
import za.co.admatech.service.cart_domain_service.ICartService;

import java.util.List;

@RestController
@RequestMapping("/carts")
@CrossOrigin(origins = "*")
public class CartController {
    private final ICartService cartService;

    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Cart> create(@Valid @RequestBody Cart cart) {
        Cart created = cartService.create(cart);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> read(@PathVariable String id) {
        Cart cart = cartService.read(id);
        return ResponseEntity.ok(cart);
    }

    @PutMapping
    public ResponseEntity<Cart> update(@Valid @RequestBody Cart cart) {
        Cart updated = cartService.update(cart);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean deleted = cartService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getAll() {
        return ResponseEntity.ok(cartService.getAll());
    }
}