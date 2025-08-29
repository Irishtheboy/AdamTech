package za.co.admatech.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import za.co.admatech.domain.Address;
import za.co.admatech.domain.Cart;
import za.co.admatech.domain.Customer;

import static org.junit.jupiter.api.Assertions.*;

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

        // Create Customer
        customer = restTemplate.postForObject(
                BASE_URL + "/create",
                new Customer.Builder()
                        .setFirstName("John")
                        .setLastName("Doe")
                        .setEmail("john@example.com")
                        .setPhoneNumber("1234567890")
                        .setAddress(address)
                        .setCart(cart)
                        .build(),
                Customer.class
        );

        assertNotNull(customer);
        assertNotNull(customer.getCustomerId());
    }

    @Test
    void a_create() {
        assertNotNull(customer);
        System.out.println("Created Customer: " + customer);
    }

    @Test
    void b_read() {
        ResponseEntity<Customer> response = restTemplate.getForEntity(
                BASE_URL + "/read/" + customer.getCustomerId(),
                Customer.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(customer.getCustomerId(), response.getBody().getCustomerId());

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
        assertNotNull(response.getBody());
        assertEquals(updatedCustomer.getCustomerId(), response.getBody().getCustomerId());
        System.out.println("Updated Customer: " + response.getBody());

        customer = response.getBody(); // Update with persisted object



    }

    @Test
    void d_delete() {
        restTemplate.delete(BASE_URL + "/delete/" + customer.getCustomerId());

        ResponseEntity<Customer> response = restTemplate.getForEntity(
                BASE_URL + "/read/" + customer.getCustomerId(),
                Customer.class
        );

        assertTrue(response.getStatusCode().is2xxSuccessful() || response.getStatusCode() == HttpStatus.NOT_FOUND);
        System.out.println("Deleted Customer with ID: " + customer.getCustomerId());
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
