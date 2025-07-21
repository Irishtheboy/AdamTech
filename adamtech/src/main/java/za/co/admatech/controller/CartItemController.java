/*CartItemController.java
  Author: Teyana Raubenheimer (230237622)
  Date: 30 May 2025
 */

package za.co.admatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.domain.CartItem;
import za.co.admatech.service.cart_item_domain_service.CartItemService;

import java.util.List;

@RestController
@RequestMapping("/cart-items")
public class CartItemController {
    private CartItemService service;

    @Autowired
    public CartItemController(CartItemService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public CartItem create(@RequestBody CartItem cartItem) {
        return service.create(cartItem);
    }

    @GetMapping("/read/{cartItemID}")
    public CartItem read(@PathVariable Long cartItemID) {
        return service.read(cartItemID);
    }

    @PutMapping("/update")
    public CartItem update(@RequestBody CartItem cartItem) {
        return service.update(cartItem);
    }

    @DeleteMapping("/delete/{cartItemID}")
    public boolean delete(@PathVariable Long cartItemID) {
        return service.delete(cartItemID);
    }

    @GetMapping("/getAll")
    public List<CartItem> getAll() {
        return service.getAll();
    }

}

