/*CartService.java
  Author: Teyana Raubenheimer (230237622)
  Date: 24 May 2025
 */

package za.co.admatech.service.cart_domain_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.admatech.domain.Cart;
import za.co.admatech.repository.CartRepository;

import java.util.List;

@Service
public class CartService implements ICartService {

    private CartRepository cartRepository;

    @Autowired
    CartService(CartRepository repository) {
        this.cartRepository = repository;
    }


    @Override
    public Cart create(Cart cart){
        return cartRepository.save(cart);
    }

    @Override
    public Cart read(Long cartId) {
        return cartRepository.findById(cartId).orElse(null);
    }

    @Override
    public Cart update(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public boolean delete(Long cartId) {
        cartRepository.deleteById(cartId);
        return true;
    }

    @Override
    public List<Cart> getAll() {
        return cartRepository.findAll();
    }
}
