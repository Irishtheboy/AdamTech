package za.co.admatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.domain.CartItem;
import za.co.admatech.service.CartItemService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart-items")
public class CartItemController {

    private final CartItemService service;

    @Autowired
    public CartItemController(CartItemService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<CartItem> create(@RequestBody CartItem cartItem) {
        CartItem created = service.create(cartItem);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<CartItem> read(@PathVariable Long id) {
        Optional<CartItem> item = Optional.ofNullable(service.read(id));
        return item.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update")
    public ResponseEntity<CartItem> update(@RequestBody CartItem cartItem) {
        CartItem updated = service.update(cartItem);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = service.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CartItem>> getAll() {
        List<CartItem> items = service.getAll();
        return ResponseEntity.ok(items);
    }
}
