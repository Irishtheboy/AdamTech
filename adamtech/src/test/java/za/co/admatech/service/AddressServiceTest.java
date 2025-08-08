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
import za.co.admatech.factory.AddressFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressServiceTest {

    @Autowired
    private IAddressService service;

    private static Address address = AddressFactory.createAddress(
            (short) 12,
            "Oak Street",
            "Parklands",
            "Cape Town",
            "Western Cape",
            (short) 7441
    );

    @Test
    @Order(1)
    void create() {
        Address created = service.create(address);
        assertNotNull(created);
        assertNotNull(created.getAddressID());
        address = created; // save the auto-generated ID
        System.out.println("Created: " + created);
    }

    @Test
    @Order(2)
    void read() {
        Address read = service.read(address.getAddressID());
        assertNotNull(read);
        assertEquals(address.getAddressID(), read.getAddressID());
        System.out.println("Read: " + read);
    }

    @Test
    @Order(3)
    void update() {
        Address updated = new Address.Builder()
                .copy(address)
                .setCity("Stellenbosch")
                .build();

        Address result = service.update(updated);
        assertNotNull(result);
        assertEquals("Stellenbosch", result.getCity());
        System.out.println("Updated: " + result);
    }

    @Test
    @Order(4)
    void delete() {
        boolean success = service.delete(address.getAddressID());
        assertTrue(success);
        System.out.println("Deleted address with ID: " + address.getAddressID());
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
