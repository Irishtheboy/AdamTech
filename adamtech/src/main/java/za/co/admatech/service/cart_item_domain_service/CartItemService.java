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

    private CartItemRepository cartItemRepository;

    @Autowired CartItemService(CartItemRepository repository) {
        this.cartItemRepository = repository;
    }

    @Override
    public CartItem create(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem read(Long id) {
        return cartItemRepository.findById(id).orElse(null);
    }

    @Override
    public CartItem update(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public boolean delete(Long id) {
        cartItemRepository.deleteById(id);
        return true;
    }

    @Override
    public List<CartItem> getAll() {
        return cartItemRepository.findAll();
    }
}
