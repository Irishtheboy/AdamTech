package za.co.admatech.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.admatech.domain.Address;
import za.co.admatech.domain.Customer;
import za.co.admatech.domain.Cart;
import za.co.admatech.repository.CustomerRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        // Clear database before each test
        customerRepository.deleteAll();

        Address address = new Address.Builder()
                .setStreetNumber((short) 123)
                .setStreetName("Main St")
                .setSuburb("Central")
                .setCity("Cape Town")
                .setProvince("Western Cape")
                .setPostalCode((short) 8001)
                .build();

        testCustomer = new Customer.Builder()
                .setFirstName("Franco")
                .setLastName("Snake")
                .setEmail("deltasnakeEater@example.com") // PK
                .setPhoneNumber("0812345678")
                .setPassword("123456")
                .setAddress(address)
                .build();
    }

    @Test
    @Order(1)
    void testCreateCustomer() {
        Customer created = customerService.create(testCustomer);
        assertNotNull(created);
        assertEquals("deltasnakeEater@example.com", created.getEmail());
        assertEquals("Franco", created.getFirstName());

        // ✅ Check automatic cart creation
        Cart cart = created.getCart();
        assertNotNull(cart, "Customer should have a cart automatically");
        assertNotNull(cart.getCartId(), "Cart should have an ID");
    }

    @Test
    @Order(2)
    void testReadCustomer() {
        customerService.create(testCustomer);
        Customer found = customerService.read("deltasnakeEater@example.com");
        assertNotNull(found);
        assertEquals("deltasnakeEater@example.com", found.getEmail());
        assertEquals("Franco", found.getFirstName());
    }

    @Test
    @Order(3)
    void testUpdateCustomer() {
        customerService.create(testCustomer);

        Customer updatedCustomer = new Customer.Builder()
                .copy(testCustomer)
                .setPhoneNumber("0829999999")
                .build();

        Customer updated = customerService.update(updatedCustomer);
        assertEquals("0829999999", updated.getPhoneNumber());
    }

    @Test
    @Order(4)
    @Disabled
    void testDeleteCustomer() {
        customerService.create(testCustomer);

        boolean deleted = customerService.delete("deltasnakeEater@example.com");
        assertTrue(deleted);

        assertNull(customerService.read("deltasnakeEater@example.com"));
    }

    @Test
    @Order(5)
    void testReadNonExistentCustomer() {
        Customer found = customerService.read("nonexistent@example.com");
        assertNull(found, "Reading a non-existent customer should return null");
    }
}
