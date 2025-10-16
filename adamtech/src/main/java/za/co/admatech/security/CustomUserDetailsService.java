package za.co.admatech.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import za.co.admatech.domain.Customer;
import za.co.admatech.repository.CustomerRepository;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public CustomUserDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found with email: " + email));

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        return new org.springframework.security.core.userdetails.User(
                customer.getEmail(),
                customer.getPassword(), // <-- do NOT encode here
                authorities
        );
    }

}
