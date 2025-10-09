package za.co.admatech.factory;

/**
 * AddressFactoryTest.java
 * Factory test class for AddressFactory
 *
 * Author: Rorisang Makgana (230602363)
 */

import org.junit.jupiter.api.*;
import za.co.admatech.domain.Address;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressFactoryTest {

    private static Address address;

    @BeforeAll
    public static void setup() {
        address = AddressFactory.createAddress(
                (short)1L,                    // addressID
                // streetNumber
                "Devin's Chapman",     // streetName
                "Cravenwood",          // suburb
                "Mulburrey",           // city
                "Lancashire",          // province
                (short) 1299           // postalCode
        );
    }

    @Test
    @Order(1)
    void createAddress() {
        assertNotNull(address);
        assertEquals((short) 1, address.getStreetNumber());
        assertEquals("Devin's Chapman", address.getStreetName());
        assertEquals("Cravenwood", address.getSuburb());
        assertEquals("Mulburrey", address.getCity());
        assertEquals("Lancashire", address.getProvince());
        assertEquals((short) 1299, address.getPostalCode());
        System.out.println("Created Address: " + address);
    }
}
