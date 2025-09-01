package za.co.admatech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.admatech.DTO.CustomerRegistrationRequest;
import za.co.admatech.domain.Address;
import za.co.admatech.domain.Customer;
import za.co.admatech.domain.Payment;
import za.co.admatech.factory.AddressFactory;
import za.co.admatech.factory.CustomerFactory;
import za.co.admatech.repository.CustomerRepository;
import za.co.admatech.util.Helper;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer read(Long customerId) {
        return customerRepository.findById(customerId).orElse(null);
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
    public boolean delete(Long customerId) {
        customerRepository.deleteById(customerId);
        return true;
    }

    @Override
    public Customer createFromRegistrationRequest(CustomerRegistrationRequest request) {
        // Enhanced validation using Helper class
        if (!Helper.isValidName(request.getFirstName())) {
            throw new IllegalArgumentException("Invalid first name. Must be 2-50 characters with letters, spaces, hyphens, or apostrophes only.");
        }
        
        if (!Helper.isValidName(request.getLastName())) {
            throw new IllegalArgumentException("Invalid last name. Must be 2-50 characters with letters, spaces, hyphens, or apostrophes only.");
        }
        
        if (!Helper.isValidEmail(request.getEmail())) {
            throw new IllegalArgumentException("Invalid email address.");
        }
        
        if (!Helper.isValidPhoneNumber(request.getPhoneNumber())) {
            throw new IllegalArgumentException("Invalid phone number. Must be a valid international or local format.");
        }
        
        if (!Helper.isValidPassword(request.getPassword())) {
            throw new IllegalArgumentException("Invalid password. Must be at least 8 characters long.");
        }
        
        // Validate address fields
        if (!Helper.isValidStreetAddress(request.getAddress().getStreet())) {
            throw new IllegalArgumentException("Invalid street address. Must be 5-100 characters long.");
        }
        
        if (!Helper.isValidCity(request.getAddress().getCity())) {
            throw new IllegalArgumentException("Invalid city name. Must be 2-50 characters with letters, spaces, hyphens, or apostrophes only.");
        }
        
        // Hash the password for storage
        String hashedPassword = Helper.hashPassword(request.getPassword());
        
        // Parse address components from frontend "street" field
        String fullStreet = request.getAddress().getStreet();
        short streetNumber = 0;
        String streetName = fullStreet; // Default to full street
        
        // Try to extract street number from the beginning of the string
        try {
            String[] parts = fullStreet.trim().split("\\s+", 2);
            if (parts.length > 0 && parts[0].matches("\\d+")) {
                streetNumber = Short.parseShort(parts[0]);
                streetName = parts.length > 1 ? parts[1] : "";
            }
        } catch (NumberFormatException e) {
            // Keep defaults if parsing fails
        }

        // Parse postal code
        short postalCode = 0;
        try {
            String zipCode = request.getAddress().getZipCode();
            if (zipCode != null && zipCode.matches("\\d+")) {
                postalCode = Short.parseShort(zipCode);
                // Validate postal code range
                if (!Helper.isValidPostalCode(postalCode)) {
                    throw new IllegalArgumentException("Invalid postal code. Must be between 1000-9999.");
                }
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid postal code format. Must be numeric.");
        }

        // Create Address using Builder directly (factory method has issues)
        Address address = new Address.Builder()
                .setStreetNumber(streetNumber)
                .setStreetName(streetName)
                .setSuburb("") // Default empty suburb
                .setCity(request.getAddress().getCity())
                .setProvince(request.getAddress().getProvince() != null ? request.getAddress().getProvince() : "Unknown")
                .setPostalCode(postalCode)
                .build();

        // Create Customer using Builder directly with hashed password
        return new Customer.Builder()
                .setFirstName(request.getFirstName())
                .setLastName(request.getLastName())
                .setEmail(request.getEmail())
                .setAddress(address)
                .setPhoneNumber(request.getPhoneNumber())
                .setPasswordHash(hashedPassword)
                .build();
    }
}
