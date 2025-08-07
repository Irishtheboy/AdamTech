/*
CartItemController.java
Author: Naqeebah Khan (219099073)
Date: 03 June 2025 */
package za.co.admatech.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.domain.CartItem;
import za.co.admatech.service.cart_item_domain_service.ICartItemService;

import java.util.List;

@RestController
@RequestMapping("/cart-items")
@CrossOrigin(origins = "*")
public class CartItemController {
    private final ICartItemService cartItemService;

    public CartItemController(ICartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping
    public ResponseEntity<CartItem> create(@Valid @RequestBody CartItem cartItem) {
        CartItem created = cartItemService.create(cartItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItem> read(@PathVariable String id) {
        CartItem cartItem = cartItemService.read(id);
        return ResponseEntity.ok(cartItem);
    }

    @PutMapping
    public ResponseEntity<CartItem> update(@Valid @RequestBody CartItem cartItem) {
        CartItem updated = cartItemService.update(cartItem);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean deleted = cartItemService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getAll() {
        return ResponseEntity.ok(cartItemService.getAll());
    }
}