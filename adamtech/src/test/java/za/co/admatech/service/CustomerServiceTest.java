package za.co.admatech.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Address;
import za.co.admatech.domain.Customer;
import za.co.admatech.repository.CustomerRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer testCustomer;

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
                .setCustomerID("C001")
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
        assertEquals("C001", created.getCustomerID());
        assertEquals("John", created.getFirstName());
    }

    @Test
    @Order(2)
    void testRead() {
        customerService.create(testCustomer);
        Customer found = customerService.read("C001");
        assertNotNull(found);
        assertEquals("john.doe@example.com", found.getEmail());
    }

    @Test
    @Order(3)
    void testUpdate() {
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
    void testDelete() {
        customerService.create(testCustomer);
        boolean deleted = customerService.delete("C001");

        assertTrue(deleted);
        assertNull(customerService.read("C001"));
    }
}
