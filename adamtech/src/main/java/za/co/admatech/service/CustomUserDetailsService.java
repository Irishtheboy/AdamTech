package za.co.admatech.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import za.co.admatech.domain.Customer;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerService customerService;

    public CustomUserDetailsService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerService.read(email);
        if (customer == null) {
            throw new UsernameNotFoundException("Customer not found with email: " + email);
        }
        return customer;
    }
}