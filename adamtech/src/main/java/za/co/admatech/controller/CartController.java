/*





CartController.java



Author: Teyana Raubenheimer (230237622)



Date: 30 May 2025 */ package za.co.admatech.controller;

import jakarta.validation.Valid; import org.springframework.http.HttpStatus; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*; import za.co.admatech.domain.Cart; import za.co.admatech.service.cart_domain_service.CartService;

import java.util.List;

@RestController @RequestMapping("/carts")
@CrossOrigin(origins = "*") // Adjust origins as needed for production public class CartController { private final CartService cartService;
public class CartController{
    private CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Cart> create(@Valid @RequestBody Cart cart) {
        Cart created = cartService.create(cart);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{cartID}")
    public ResponseEntity<Cart> read(@PathVariable Long cartID) {
        Cart cart = cartService.read(cartID);
        return ResponseEntity.ok(cart);
    }

    @PutMapping
    public ResponseEntity<Cart> update(@Valid @RequestBody Cart cart) {
        Cart updated = cartService.update(cart);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{cartID}")
    public ResponseEntity<Void> delete(@PathVariable Long cartID) {
        boolean deleted = cartService.delete(cartID);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getAll() {
        return ResponseEntity.ok(cartService.getAll());
    }

}