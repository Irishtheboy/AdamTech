package za.co.admatech.factory;
/**
 * CustomerFactoryTest.java
 * CustomerFactoryTest Factory Test class
 *
 * Author: Rorisang Makgana(230602363)
 */

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import za.co.admatech.domain.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerFactoryTest {

    private static Address address = AddressFactory.createAddress(
            (short)07111,
            "Bakersdraft",
            "Kuils River",
            "Cape Town",
            "Western Cape",
            (short) 7440);

    private static Customer customer = CustomerFactory.createCustomer(
            "Dennis",
            "Estrange",
            "estranged@commons.net",
            "password123",
            address
    );

    @Test
    @Order(1)
    void testCreateCustomer(){
        assertNotNull(customer);
        assertNotNull(customer.getPassword());
        assertEquals("Dennis", customer.getFirstName());
        assertEquals("Estrange", customer.getLastName());
        assertEquals("estranged@commons.net", customer.getEmail());
        assertEquals(address, customer.getAddress());
        System.out.println("Customer created: " + customer);
    }

}