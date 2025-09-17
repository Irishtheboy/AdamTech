package za.co.admatech.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Address;
import za.co.admatech.domain.Customer;
import za.co.admatech.domain.Cart;
import za.co.admatech.repository.CustomerRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

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
