/*CartService.java
  Author: Teyana Raubenheimer (230237622)
  Date: 24 May 2025
 */

package za.co.admatech.service.cart_domain_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.admatech.domain.Cart;
import za.co.admatech.domain.Customer;
import za.co.admatech.repository.CartRepository;

import java.util.List;

@Service
public class CartService implements ICartService {

    private CartRepository repository;

    @Autowired CartService(CartRepository repository) {
        this.repository = repository;
    }


    @Override
    public Cart create(Cart cart) {
        return this.repository.save(cart);
    }

    @Override
    public Cart read(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public Cart update(Cart cart) {
        return this.repository.save(cart);
    }

    @Override
    public boolean delete(Long id) {
        this.repository.deleteById(id);
        return true;
    }
    @Override
    public Cart getCartByCustomer(Customer customer) {
        return repository.findByCustomer(customer)
                .orElseThrow(() -> new RuntimeException("Cart not found for customer: " + customer.getCustomerId()));
    }


    @Override
    public List<Cart> getAll() {
        return this.repository.findAll();
    }
}
