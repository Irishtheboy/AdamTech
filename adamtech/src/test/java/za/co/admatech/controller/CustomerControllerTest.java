package za.co.admatech.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.co.admatech.domain.Address;
import za.co.admatech.domain.Customer;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerControllerTest {
    private static Customer customer;
    private static Address address;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "/customer";

    @BeforeAll
    public static void setup() {
        // Create Address using Builder (assuming AddressFactory uses Builder pattern)
        address = new Address.Builder()
                .setStreetNumber((short) 12)
                .setStreetName("Devin's Chapman")
                .setSuburb("Cravenwood")
                .setCity("Mulburrey")
                .setProvince("Lancashire")
                .setPostalCode((short) 1299)
                .build();

        // Create Customer using Builder (assuming CustomerFactory uses Builder pattern)
        customer = new Customer.Builder()
                .setCustomerId(1L) // Use Long instead of String
                .setFirstName("Rorisang")
                .setLastName("Makgana")
                .setEmail("radamtech@corporate.adamtect")
                .setAddress(address)
                .setPhoneNumber("1234567890") // Added to avoid null phoneNumber
                .build();
    }

    @Test
    @Order(1)
    void create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Customer> response = restTemplate.postForEntity(url, customer, Customer.class);
        System.out.println("Create response: " + response);
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(customer.getCustomerId(), response.getBody().getCustomerId());
        assertEquals(customer.getEmail(), response.getBody().getEmail());
        System.out.println("Created customer: " + response.getBody());
    }

    @Test
    @Order(2)
    void read() {
        String url = BASE_URL + "/read/" + customer.getCustomerId();
        ResponseEntity<Customer> response = restTemplate.getForEntity(url, Customer.class);
        System.out.println("Read response: " + response);
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(customer.getCustomerId(), response.getBody().getCustomerId());
        System.out.println("Read customer: " + response.getBody());
    }

    @Test
    @Order(3)
    void update() {
        Customer updatedCustomer = new Customer.Builder()
                .copy(customer)
                .setFirstName("UpdatedRorisang")
                .setLastName("UpdatedMakgana")
                .build();
        String url = BASE_URL + "/update";
        ResponseEntity<Customer> response = restTemplate.postForEntity(url, updatedCustomer, Customer.class);
        System.out.println("Update response: " + response);
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(updatedCustomer.getCustomerId(), response.getBody().getCustomerId());
        assertEquals("UpdatedRorisang", response.getBody().getFirstName());
        assertEquals("UpdatedMakgana", response.getBody().getLastName());
        System.out.println("Updated customer: " + response.getBody());
    }

    @Test
    @Order(4)
    void delete() {
        String url = BASE_URL + "/delete/" + customer.getCustomerId();
        restTemplate.delete(url);

        // Verify deletion by attempting to read the customer
        String readUrl = BASE_URL + "/read/" + customer.getCustomerId();
        ResponseEntity<Customer> response = restTemplate.getForEntity(readUrl, Customer.class);
        System.out.println("Read after delete response: " + response);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
        System.out.println("Deleted customer, read response: " + response.getBody());
    }
}