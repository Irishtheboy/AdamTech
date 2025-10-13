package za.co.admatech.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import za.co.admatech.domain.Address;
import za.co.admatech.domain.Customer;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CustomerServiceSimpleTest {

    @Autowired
    private CustomerService customerService;

    @Test
    void testCreateCustomerWithCart() {
        // Create address
        Address address = new Address.Builder()
                .setStreetNumber((short) 123)
                .setStreetName("Test Street")
                .setSuburb("Test Suburb")
                .setCity("Test City")
                .setProvince("Test Province")
                .setPostalCode((short) 1234)
                .build();

        // Create customer
        Customer customer = new Customer.Builder()
                .setEmail("test@example.com")
                .setFirstName("Test")
                .setLastName("User")
                .setPassword("password123")
                .setAddress(address)
                .setPhoneNumber("1234567890")
                .build();

        // Create customer (which should create a cart)
        Customer savedCustomer = customerService.create(customer);

        // Verify customer and cart are created
        assertNotNull(savedCustomer);
        assertNotNull(savedCustomer.getCart());
        assertNotNull(savedCustomer.getCart().getCartId());
        assertEquals(savedCustomer, savedCustomer.getCart().getCustomer());
        
        System.out.println("Customer created successfully with cart ID: " + savedCustomer.getCart().getCartId());
    }
}
