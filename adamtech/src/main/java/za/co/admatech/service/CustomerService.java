package za.co.admatech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.admatech.domain.Cart;
import za.co.admatech.domain.Customer;
import za.co.admatech.repository.CartRepository;
import za.co.admatech.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CartRepository cartRepository) {
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public Customer create(Customer customer) {
        // Save customer first (since Customer owns the relationship)
        Customer savedCustomer = customerRepository.save(customer);
        
        // Create cart and link to saved customer
        Cart cart = new Cart();
        cart.setCustomer(savedCustomer);
        Cart savedCart = cartRepository.save(cart);
        
        // Update customer with cart reference
        savedCustomer.setCart(savedCart);
        return customerRepository.save(savedCustomer);
    }

    @Override
    public Customer read(String email) {
        return customerRepository.findById(email).orElse(null);
    }

    @Override
    public Customer update(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public boolean delete(String email) {
        if (customerRepository.existsById(email)) {
            customerRepository.deleteById(email);
            return true;
        }
        return false;
    }
}
