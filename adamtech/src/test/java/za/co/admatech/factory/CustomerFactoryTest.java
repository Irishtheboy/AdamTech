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
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.*;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
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

    @Test
    void buildCustomer_ShouldCreateUniqueInstances_WhenCalledMultipleTimes() {
        // Given
        Long customerId = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";

        // When
        Customer customer1 = CustomerFactory.buildCustomer(
                customerId, firstName, lastName, email, null, null, null
        );
        Customer customer2 = CustomerFactory.buildCustomer(
                customerId, firstName, lastName, email, null, null, null
        );

        // Then
        assertNotNull(customer1);
        assertNotNull(customer2);
        assertNotSame(customer1, customer2); // Different instances
        assertEquals(customer1.getCustomerId(), customer2.getCustomerId()); // Same data
        assertEquals(customer1.getFirstName(), customer2.getFirstName());
        assertEquals(customer1.getLastName(), customer2.getLastName());
        assertEquals(customer1.getEmail(), customer2.getEmail());
    }

    @Test
    void buildCustomer_ShouldPreserveAddressRelationship() {
        // Given
        Address address = AddressFactory.buildAddress(
                456, "Second Street", "Uptown", "Los Angeles", "CA", (short) 90001
        );

        // When
        Customer customer = CustomerFactory.buildCustomer(
                1L, "Alice", "Johnson", "alice@example.com", address, null, "+1234567890"
        );

        // Then
        assertNotNull(customer.getAddress());
        assertEquals(address.getStreetNumber(), customer.getAddress().getStreetNumber());
        assertEquals(address.getStreetName(), customer.getAddress().getStreetName());
        assertEquals(address.getCity(), customer.getAddress().getCity());
        assertEquals(address.getProvince(), customer.getAddress().getProvince());
    }
}
