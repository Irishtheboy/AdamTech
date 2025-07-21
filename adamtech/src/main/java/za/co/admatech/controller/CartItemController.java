/*





CartItemController.java



Author: Teyana Raubenheimer (230237622)



Date: 30 May 2025 */ package za.co.admatech.controller;

import jakarta.validation.Valid; import org.springframework.http.HttpStatus; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*; import za.co.admatech.domain.CartItem; import za.co.admatech.service.cart_item_domain_service.CartItemService;

import java.util.List;

@RestController
@RequestMapping("/cart-items")
@CrossOrigin(origins = "*") // Adjust origins as needed for production public class CartItemController { private final CartItemService cartItemService;
public class CartItemController{
    private CartItemService cartItemService;
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping
    public ResponseEntity<CartItem> create(@Valid @RequestBody CartItem cartItem) {
        CartItem created = cartItemService.create(cartItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{cartItemID}")
    public ResponseEntity<CartItem> read(@PathVariable Long cartItemID) {
        CartItem cartItem = cartItemService.read(cartItemID);
        return ResponseEntity.ok(cartItem);
    }

    @PutMapping
    public ResponseEntity<CartItem> update(@Valid @RequestBody CartItem cartItem) {
        CartItem updated = cartItemService.update(cartItem);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{cartItemID}")
    public ResponseEntity<Void> delete(@PathVariable Long cartItemID) {
        boolean deleted = cartItemService.delete(cartItemID);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getAll() {
        return ResponseEntity.ok(cartItemService.getAll());
    }

}