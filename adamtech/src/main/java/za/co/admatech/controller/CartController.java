/*CartController.java
  Author: Teyana Raubenheimer (230237622)
  Date: 30 May 2025
 */

package za.co.admatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.domain.Cart;
import za.co.admatech.service.cart_domain_service.CartService;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private CartService service;

    @Autowired
    public CartController(CartService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Cart create(@RequestBody Cart cart) {

        return service.create(cart);
    }

    @GetMapping("/read/{cartID}")
    public Cart read(@PathVariable String cartID) {

        return service.read(cartID);
    }

    @PutMapping("/update")
    public Cart update(@RequestBody Cart cart) {

        return service.update(cart);
    }

    @DeleteMapping("/delete/{cartID}")
    public boolean delete(@PathVariable String cartID) {

        return service.delete(cartID);
    }

    @GetMapping("/getAll")
    public List<Cart> getAll() {
        return service.getAll();
    }

}
