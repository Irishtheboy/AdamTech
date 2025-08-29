package za.co.admatech.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Address;
import za.co.admatech.domain.Customer;
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
    private Long createdCustomerId; // store generated ID

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();

        Address address = new Address.Builder()
                .setStreetNumber((short)123)
                .setStreetName("Main St")
                .setSuburb("Central")
                .setCity("Cape Town")
                .setProvince("Western Cape")
                .setPostalCode((short)8001)
                .build();

        testCustomer = new Customer.Builder()
                .setFirstName("John")
                .setLastName("Doe")
                .setEmail("john.doe@example.com")
                .setPhoneNumber("0812345678")
                .setAddress(address)
                .build();
    }

    @Test
    @Order(1)
    void testCreate() {
        Customer created = customerService.create(testCustomer);
        assertNotNull(created);
        assertNotNull(created.getCustomerId());
        createdCustomerId = created.getCustomerId(); // save the ID
        assertEquals("John", created.getFirstName());
    }

    @Test
    @Order(2)
    void testRead() {
        Customer created = customerService.create(testCustomer);
        Long id = created.getCustomerId();

        Customer found = customerService.read(id);
        assertNotNull(found);
        assertEquals("john.doe@example.com", found.getEmail());
    }

    @Test
    @Order(3)
    void testUpdate() {
        Customer created = customerService.create(testCustomer);
        Long id = created.getCustomerId();

        Customer updatedCustomer = new Customer.Builder()
                .copy(created)
                .setPhoneNumber("0829999999")
                .build();

        Customer updated = customerService.update(updatedCustomer);
        assertEquals("0829999999", updated.getPhoneNumber());
    }

    @Test
    @Order(4)
    @Disabled
    void testDelete() {
        Customer created = customerService.create(testCustomer);
        Long id = created.getCustomerId();

        boolean deleted = customerService.delete(id);
        assertTrue(deleted);

        assertNull(customerService.read(id));
    }
}
