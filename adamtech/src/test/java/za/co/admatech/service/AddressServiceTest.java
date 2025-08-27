/*
 * AddressServiceTest.java
 * Author: Rorisang Makgana (230602363)
 * Date: 08 August 2025
 */
package za.co.admatech.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Address;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressServiceTest {

    @Autowired
    private IAddressService service;

    private static Address address = new Address.Builder()
            .setStreetNumber((short) 12)
            .setStreetName("Oak Street")
            .setSuburb("Parklands")
            .setCity("Cape Town")
            .setProvince("Western Cape")
            .setPostalCode((short) 7441)
            .build();

    @Test
    @Order(1)
    void create() {
        Address created = service.create(address);
        assertNotNull(created);
        assertNotNull(created.getAddressId());
        assertEquals(address.getStreetName(), created.getStreetName());
        assertEquals(address.getPostalCode(), created.getPostalCode());
        address = created; // Update with generated ID
        System.out.println("Created: " + created);
    }

    @Test
    @Order(2)
    void read() {
        Address read = service.read(address.getAddressId());
        assertNotNull(read);
        assertEquals(address.getAddressId(), read.getAddressId());
        assertEquals(address.getCity(), read.getCity());
        System.out.println("Read: " + read);
    }

    @Test
    @Order(3)
    void update() {
        Address updated = new Address.Builder()
                .copy(address)
                .setCity("Stellenbosch")
                .setPostalCode((short) 7580)
                .build();

        Address result = service.update(updated);
        assertNotNull(result);
        assertEquals(address.getAddressId(), result.getAddressId());
        assertEquals("Stellenbosch", result.getCity());
        assertEquals((short) 7580, result.getPostalCode());
        System.out.println("Updated: " + result);
    }

    @Test
    @Order(4)
    void delete() {
        boolean success = service.delete(address.getAddressId());
        assertTrue(success);

        // Verify deletion
        Address read = service.read(address.getAddressId());
        assertNull(read);
        System.out.println("Deleted address with ID: " + address.getAddressId());
    }

    @Test
    @Order(5)
    void getAll() {
        List<Address> all = service.getAll();
        assertNotNull(all);
        assertTrue(all.size() >= 0);
        System.out.println("All addresses: " + all);
    }
}