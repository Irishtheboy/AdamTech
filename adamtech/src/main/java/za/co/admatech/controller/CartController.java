package za.co.admatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.domain.Cart;
import za.co.admatech.domain.CartItem;
import za.co.admatech.service.CartService;

import java.util.List;


@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService service;

    @Autowired
    public CartController(CartService service) {
        this.service = service;
    }
    // Get cart by customer email, create if it doesn't exist
    @GetMapping("/customer/{email}")
    public ResponseEntity<Cart> getCartByCustomer(@PathVariable String email) {
        try {
            Cart cart = service.getCartByCustomerEmail(email);

            // Populate each CartItem's Product
            if (cart.getCartItems() != null) {
                for (CartItem item : cart.getCartItems()) {
                    service.read(item.getCartItemId()); // ensures product is populated
                }
            }

            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    // ✅ Create new cart
    @PostMapping("/create")
    public ResponseEntity<Cart> create(@RequestBody Cart cart) {
        if (cart.getCartId() != null) {
            return ResponseEntity.badRequest().build(); // cannot create if cartId exists
        }
        return ResponseEntity.ok(service.create(cart));
    }

    // ✅ Update existing cart
    @PutMapping("/update")
    public ResponseEntity<Cart> update(@RequestBody Cart cart) {
        if (cart.getCartId() == null) {
            return ResponseEntity.badRequest().build(); // cannot update if no cartId
        }
        return ResponseEntity.ok(service.update(cart));
    }

    @GetMapping("/read/{cartID}")
    public ResponseEntity<Cart> read(@PathVariable Long cartID) {
        Cart cart = service.read(cartID);
        return cart != null ? ResponseEntity.ok(cart) : ResponseEntity.notFound().build();
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
