/*CartItemService.java
  Author: Teyana Raubenheimer (230237622)
  Date: 23 May 2025
 */

package za.co.admatech.service.cart_item_domain_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.admatech.domain.CartItem;
import za.co.admatech.repository.CartItemRepository;

import java.util.List;

@Service
public class CartItemService implements ICartItemService {

    private CartItemRepository repository;

    @Autowired CartItemService(CartItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public CartItem create(CartItem cartItem) {
        return this.repository.save(cartItem);
    }

    @Override
    public CartItem read(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public CartItem update(CartItem cartItem) {
        return this.repository.save(cartItem);
    }

    @Override
    public boolean delete(Long id) {
        this.repository.deleteById(id);
        return true;
    }

    @Override
    public List<CartItem> getAll() {
        return this.repository.findAll();
    }
}
