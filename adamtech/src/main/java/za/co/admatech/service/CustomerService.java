package za.co.admatech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(CustomerRepository customerRepository,
                           CartRepository cartRepository,
                           PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Customer create(Customer customer) {
        // ✅ Hash the password before saving
        String encodedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);

        Cart cart = new Cart();
        cartRepository.save(cart);
        customer.setCart(cart);
        return customerRepository.save(customer);
    }

    @Override
    public Customer read(String email) {
        return customerRepository.findById(email).orElse(null);
    }

    @Override
    public Customer update(Customer customer) {
        // ✅ Handle password update properly
        Optional<Customer> existingCustomer = customerRepository.findById(customer.getEmail());
        if (existingCustomer.isPresent()) {
            Customer updatedCustomer = existingCustomer.get();

            // Update fields
            updatedCustomer.setFirstName(customer.getFirstName());
            updatedCustomer.setLastName(customer.getLastName());
            updatedCustomer.setPhoneNumber(customer.getPhoneNumber());
            updatedCustomer.setAddress(customer.getAddress());

            // Only update password if a new one is provided
            if (customer.getPassword() != null && !customer.getPassword().isEmpty()) {
                String encodedPassword = passwordEncoder.encode(customer.getPassword());
                updatedCustomer.setPassword(encodedPassword);
            }

            return customerRepository.save(updatedCustomer);
        }
        return null;
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