package za.co.admatech.service;/*
AddressServiceTest.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025
Description: This test class contains integration tests for the AddressService class,
verifying the functionality of create, read, update, delete, and getAll methods
using Spring Boot without mocks.
*/


import jakarta.persistence.EntityNotFoundException;
import za.co.admatech.domain.Address;
import za.co.admatech.service.address_domain_service.AddressService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class AddressServiceTest {

    @Autowired
    private AddressService service;

    // Mock Address setup
    private final Long VALID_ID = 1L;
    private final Address address = new Address.Builder()
            .addressId(VALID_ID)
            .streetName("Main St") // Example field, ensure it passes Helper.isValidAddress
            .build();

    @Test
    void a_create() {
        Address created = service.create(address);
        assertNotNull(created);
        assertEquals(VALID_ID, created.getAddressId());
        System.out.println("Created: " + created);
    }

    @Test
    void b_read() {
        Address saved = service.create(address);
        Address read = service.read(saved.getAddressId());
        assertNotNull(read);
        assertEquals(VALID_ID, read.getAddressId());
        System.out.println("Read: " + read);
    }

    @Test
    void c_update() {
        Address saved = service.create(address);
        Address updated = saved.copy();
        updated.setStreetName("New St"); // Example update logic
        Address result = service.update(updated);
        assertNotNull(result);
        assertEquals(VALID_ID, result.getAddressId());
        assertEquals("New St", result.getStreetName()); // Verify updated field
        System.out.println("Updated: " + result);
    }

    @Test
    void d_delete() {
        Address saved = service.create(address);
        boolean deleted = service.delete(saved.getAddressId());
        assertTrue(deleted);
        assertThrows(EntityNotFoundException.class, () -> service.read(saved.getAddressId()));
        System.out.println("Deleted: " + deleted);
    }

    @Test
    void e_getAddresses() {
        service.create(address);
        List<Address> addresses = service.getAll();
        assertNotNull(addresses);
        assertFalse(addresses.isEmpty());
        addresses.forEach(System.out::println);
    }
}