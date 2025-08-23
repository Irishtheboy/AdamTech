/*CartController.java
  Author: Teyana Raubenheimer (230237622)
  Date: 30 May 2025
 */

package za.co.admatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.domain.Cart;
import za.co.admatech.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService service;

    @Autowired
    public CartController(CartService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<Cart> create(@RequestBody Cart cart) {
        return ResponseEntity.ok(service.create(cart));
    }

    @GetMapping("/read/{cartID}")
    public ResponseEntity<Cart> read(@PathVariable Long cartID) {
        Cart cart = service.read(cartID);
        return cart != null ? ResponseEntity.ok(cart) : ResponseEntity.notFound().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Cart> update(@RequestBody Cart cart) {
        return ResponseEntity.ok(service.update(cart));
    }

    @DeleteMapping("/delete/{cartID}")
    public ResponseEntity<Void> delete(@PathVariable Long cartID) {
        boolean deleted = service.delete(cartID);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Cart>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
