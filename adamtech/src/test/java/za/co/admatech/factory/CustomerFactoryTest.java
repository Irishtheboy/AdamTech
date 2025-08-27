package za.co.admatech.factory;

import org.junit.jupiter.api.Test;
import za.co.admatech.domain.Address;
import za.co.admatech.domain.Cart;
import za.co.admatech.domain.Customer;

import static org.junit.jupiter.api.Assertions.*;

class CustomerFactoryTest {

    @Test
    void buildCustomer_ShouldReturnValidCustomer_WhenAllParametersProvided() {
        // Given
        Long customerId = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        Address address = AddressFactory.buildAddress(
                123, "Main Street", "Downtown", "New York", "NY", (short) 10001
        );
        Cart cart = null;
        String phoneNumber = "+1234567890";

        // When
        Customer customer = CustomerFactory.buildCustomer(
                customerId, firstName, lastName, email, address, cart, phoneNumber
        );

        // Then
        assertNotNull(customer);
        assertEquals(customerId, customer.getCustomerId());
        assertEquals(firstName, customer.getFirstName());
        assertEquals(lastName, customer.getLastName());
        assertEquals(email, customer.getEmail());
        assertEquals(address, customer.getAddress());
        assertEquals(cart, customer.getCart());
        assertEquals(phoneNumber, customer.getPhoneNumber());
    }

    @Test
    void buildCustomer_ShouldHandleNullValues_WhenOptionalParametersNotProvided() {
        // Given
        Long customerId = 2L;
        String firstName = "Jane";
        String lastName = "Smith";
        String email = "jane.smith@example.com";
        Address address = null;
        Cart cart = null;
        String phoneNumber = null;

        // When
        Customer customer = CustomerFactory.buildCustomer(
                customerId, firstName, lastName, email, address, cart, phoneNumber
        );

        // Then
        assertNotNull(customer);
        assertEquals(customerId, customer.getCustomerId());
        assertEquals(firstName, customer.getFirstName());
        assertEquals(lastName, customer.getLastName());
        assertEquals(email, customer.getEmail());
        assertNull(customer.getAddress());
        assertNull(customer.getCart());
        assertNull(customer.getPhoneNumber());
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
