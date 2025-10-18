/*CartService.java
  Author: Teyana Raubenheimer (230237622)
  Updated: 7 Sep 2025
  Purpose: Properly handle cart creation and updates with managed entities
*/

package za.co.admatech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.admatech.domain.Cart;
import za.co.admatech.domain.CartItem;
import za.co.admatech.domain.Customer;
import za.co.admatech.domain.Product;
import za.co.admatech.repository.CartRepository;
import za.co.admatech.repository.ProductRepository;
import za.co.admatech.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public CartService(CartRepository cartRepository,
                       ProductRepository productRepository,
                       CustomerRepository customerRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Cart create(Cart cart) {
        // Ensure the Customer is managed
        Customer managedCustomer = customerRepository.findById(cart.getCustomer().getEmail())
                .orElseThrow(() -> new RuntimeException(
                        "Customer not found with ID: " + cart.getCustomer().getEmail()
                ));
        cart.setCustomer(managedCustomer);

        // Link CartItems to managed Products and set parent Cart
        if (cart.getCartItems() != null) {
            for (CartItem item : cart.getCartItems()) {
                Product managedProduct = productRepository.findById(item.getProduct().getProductId())
                        .orElseThrow(() -> new RuntimeException(
                                "Product not found: " + item.getProduct().getProductId()
                        ));
                item.setProduct(managedProduct);
                item.setCart(cart);
            }
        }

        return cartRepository.save(cart);
    }

    @Override
    public Cart read(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    @Override
    public Cart update(Cart cart) {
        // Fetch existing cart
        Cart existingCart = cartRepository.findById(cart.getCartId())
                .orElseThrow(() -> new RuntimeException(
                        "Cart not found with ID: " + cart.getCartId()
                ));

        // Ensure the Customer is managed
        Customer managedCustomer = customerRepository.findById(cart.getCustomer().getEmail())
                .orElseThrow(() -> new RuntimeException(
                        "Customer not found with ID: " + cart.getCustomer().getEmail()
                ));
        existingCart.setCustomer(managedCustomer);

        // Prepare updated CartItems
        List<CartItem> updatedItems = new ArrayList<>();
        if (cart.getCartItems() != null) {
            for (CartItem item : cart.getCartItems()) {
                Product managedProduct = productRepository.findById(item.getProduct().getProductId())
                        .orElseThrow(() -> new RuntimeException(
                                "Product not found: " + item.getProduct().getProductId()
                        ));
                item.setProduct(managedProduct);
                item.setCart(existingCart);
                updatedItems.add(item);
            }
        }

        existingCart.setCartItems(updatedItems);
        return cartRepository.save(existingCart);
    }

    @Override
    public boolean delete(Long id) {
        cartRepository.deleteById(id);
        return true;
    }

    @Override
    public Cart getCartByCustomer(Customer customer) {
        return cartRepository.findByCustomer(customer)
                .orElseThrow(() -> new RuntimeException(
                        "Cart not found for customer: " + customer.getEmail()
                ));
    }

    @Override
    public List<Cart> getAll() {
        return cartRepository.findAll();
    }
    // Inside CartService.java
    public Cart getCartByCustomerEmail(String email) {
        // Fetch the customer
        Customer customer = customerRepository.findById(email)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + email));

        // Fetch the cart, create new if not found
        return cartRepository.findByCustomer(customer)
                .orElseGet(() -> {
                    Cart newCart = new Cart.Builder()
                            .setCustomer(customer)
                            .build();
                    return create(newCart); // save new cart
                });
    }


}
