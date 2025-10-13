package za.co.admatech.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import za.co.admatech.domain.Address;
import za.co.admatech.domain.Cart;
import za.co.admatech.domain.Customer;
import za.co.admatech.factory.AddressFactory;
import za.co.admatech.factory.CustomerFactory;
import za.co.admatech.service.CustomerService;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class CustomerControllerTest {

    private static Customer customer;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "/customer";

    @BeforeEach
    void setUp() {
        // Prepare Address and Cart
        Address address = new Address.Builder()
                .setStreetName("123 Main St")
                .setCity("Testville")
                .build();

        Cart cart = new Cart.Builder().build();

        // Create Customer (email is PK now)
        customer = restTemplate.postForObject(
                BASE_URL + "/create",
                new Customer.Builder()
                        .setFirstName("John")
                        .setLastName("Doe")
                        .setEmail("john@example.com") // <-- PK
                        .setPhoneNumber("1234567890")
                        .setPassword("password")
                        .setAddress(address)
                        .setCart(cart)
                        .build(),
                Customer.class
        );

        assertNotNull(customer);
        assertNotNull(customer.getEmail()); // check PK
    }

    @Test
    void a_create() {
        assertNotNull(customer);
        System.out.println("Created Customer: " + customer);
    }

    @Test
    void b_read() {
        ResponseEntity<Customer> response = restTemplate.getForEntity(
                BASE_URL + "/read/" + customer.getEmail(), // use email instead of ID
                Customer.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(customer.getEmail(), response.getBody().getEmail());

        System.out.println("Read Customer: " + response.getBody());
    }

    @Test
    void c_update() {
        Customer updatedCustomer = new Customer.Builder()
                .copy(customer)
                .setFirstName("Jane")
                .build();

        HttpEntity<Customer> request = new HttpEntity<>(updatedCustomer);
        ResponseEntity<Customer> response = restTemplate.exchange(
                BASE_URL + "/update",
                HttpMethod.PUT,
                request,
                Customer.class
        );

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedCustomer.getEmail(), response.getBody().getEmail());
        assertEquals("Jane", response.getBody().getFirstName());

        System.out.println("Updated Customer: " + response.getBody());

        customer = response.getBody(); // update reference
    }

    @Test
    @Disabled
    void d_delete() {
        restTemplate.delete(BASE_URL + "/delete/" + customer.getEmail()); // delete by email

        ResponseEntity<Customer> response = restTemplate.getForEntity(
                BASE_URL + "/read/" + customer.getEmail(),
                Customer.class
        );

        // Either returns NOT_FOUND or null body
        assertTrue(
                response.getStatusCode() == HttpStatus.NOT_FOUND ||
                        (response.getStatusCode() == HttpStatus.OK && response.getBody() == null)
        );

        System.out.println("Deleted Customer with Email: " + customer.getEmail());
    }

    @Test
    void e_getAll() {
        ResponseEntity<Customer[]> response = restTemplate.getForEntity(
                BASE_URL + "/getAll",
                Customer[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        System.out.println("All Customers:");
        for (Customer c : response.getBody()) {
            System.out.println(c);
        }
    }
}
