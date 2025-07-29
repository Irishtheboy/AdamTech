package za.co.admatech.service;/*
CustomerServiceTest.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025
Description: This test class contains integration tests for the CustomerService class,
verifying the functionality of create, read, update, delete, and getAll methods
using Spring Boot without mocks.
*/


import jakarta.persistence.EntityNotFoundException;
import za.co.admatech.domain.Customer;
import za.co.admatech.service.customer_domain_service.CustomerService;
import za.co.admatech.util.Helper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class CustomerServiceTest {

    @Autowired
    private CustomerService service;

    // Mock Customer setup
    private final String VALID_ID = "1";
    private final Customer customer = new Customer.Builder()
            .customerId(VALID_ID)
            .firstName("John Doe") // Example field
            .build();

    @Test
    void a_create() {
        Customer created = service.create(customer);
        assertNotNull(created);
        assertEquals(VALID_ID, created.getCustomerId());
        System.out.println("Created: " + created);
    }

    @Test
    void b_read() {
        Customer saved = service.create(customer);
        Customer read = service.read(saved.getCustomerId());
        assertNotNull(read);
        assertEquals(VALID_ID, read.getCustomerId());
        System.out.println("Read: " + read);
    }

    @Test
    void c_update() {
        Customer saved = service.create(customer);
        Customer updated = saved.copy();
        updated.setFirstName("Jane Doe"); // Example update logic
        Customer result = service.update(updated);
        assertNotNull(result);
        assertEquals(VALID_ID, result.getCustomerId());
        assertEquals("Jane Doe", result.getFirstName()); // Verify updated field
        System.out.println("Updated: " + result);
    }

    @Test
    void d_delete() {
        Customer saved = service.create(customer);
        boolean deleted = service.delete(saved.getCustomerId());
        assertTrue(deleted);
        assertThrows(EntityNotFoundException.class, () -> service.read(saved.getCustomerId()));
        System.out.println("Deleted: " + deleted);
    }

    @Test
    void e_getCustomers() {
        service.create(customer);
        List<Customer> customers = service.getAll();
        assertNotNull(customers);
        assertFalse(customers.isEmpty());
        customers.forEach(System.out::println);
    }
}