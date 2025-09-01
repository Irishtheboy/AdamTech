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
            }
        } catch (NumberFormatException e) {
            // Keep default if parsing fails
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

        // Create Customer using Builder directly (factory method is incomplete)
        return new Customer.Builder()
                .setFirstName(request.getFirstName())
                .setLastName(request.getLastName())
                .setEmail(request.getEmail())
                .setAddress(address)
                .setPhoneNumber(request.getPhoneNumber())
                .build();
    }
}
