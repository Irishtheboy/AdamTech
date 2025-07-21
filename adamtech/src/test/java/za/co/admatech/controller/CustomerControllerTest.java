/*





CustomerControllerTest.java



Author: Rorisang Makgana (230602363) */ package za.co.admatech.controller;

import org.junit.jupiter.api.*; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.context.SpringBootTest; import org.springframework.boot.test.web.server.LocalServerPort; import org.springframework.http.HttpMethod; import org.springframework.http.HttpStatus; import org.springframework.http.ResponseEntity; import org.springframework.boot.test.web.client.TestRestTemplate; import za.co.admatech.domain.Address; import za.co.admatech.domain.Cart; import za.co.admatech.domain.Customer; import za.co.admatech.factory.AddressFactory; import za.co.admatech.factory.CartFactory; import za.co.admatech.factory.CustomerFactory; import za.co.admatech.repository.AddressRepository; import za.co.admatech.repository.CartRepository;

import jakarta.transaction.Transactional; import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) @TestMethodOrder(MethodOrderer.OrderAnnotation.class) @Transactional class CustomerControllerTest { @LocalServerPort private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CartRepository cartRepository;

    private String baseUrl;

    private static Customer customer;
    private static Address address;
    private static Cart cart;

    @BeforeAll
    public static void setup() {
        address = AddressFactory.createAddress(
                12L,
                (short) 12,
                "Devin's Chapman",
                "Cravenwood",
                "Mulburrey",
                "Lancashire",
                (short) 1299
        );
        cart = CartFactory.createCart(
                13L,
                null, // Customer will be set after creation
                List.of()
        );
        customer = CustomerFactory.createCustomer(
                7L,
                "Rorisang",
                "Makgana",
                "radamtech@corporate.adamtect",
                "0111111111",
                cart,
                List.of(address),
                List.of()
        );
    }

    @BeforeEach
    public void init() {
        baseUrl = "http://localhost:" + port + "/customers";
    }

    @Test
    @Order(1)
    void create() {
        addressRepository.save(address);
        cartRepository.save(cart);
        ResponseEntity<Customer> response = restTemplate.postForEntity(baseUrl, customer, Customer.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(customer.getCustomerID(), response.getBody().getCustomerID());
        assertEquals(customer.getFirstName(), response.getBody().getFirstName());
        assertEquals(customer.getLastName(), response.getBody().getLastName());
        System.out.println("Created Customer: " + response.getBody());

        // Update customer for subsequent tests
        customer = response.getBody();
    }

    @Test
    @Order(2)
    void read() {
        String url = baseUrl + "/" + customer.getCustomerID();
        ResponseEntity<Customer> response = restTemplate.getForEntity(url, Customer.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(customer.getCustomerID(), response.getBody().getCustomerID());
        assertEquals(customer.getFirstName(), response.getBody().getFirstName());
        System.out.println("Read Customer: " + response.getBody());
    }

    @Test
    @Order(3)
    void update() {
        Customer updatedCustomer = new Customer.Builder()
                .copy(customer)
                .setFirstName("UpdatedRorisang")
                .setLastName("UpdatedMakgana")
                .build();
        ResponseEntity<Customer> response = restTemplate.exchange(baseUrl, HttpMethod.PUT, new org.springframework.http.HttpEntity<>(updatedCustomer), Customer.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedCustomer.getCustomerID(), response.getBody().getCustomerID());
        assertEquals("UpdatedRorisang", response.getBody().getFirstName());
        assertEquals("UpdatedMakgana", response.getBody().getLastName());
        System.out.println("Updated Customer: " + response.getBody());
    }

    @Test
    @Order(4)
    void delete() {
        String url = baseUrl + "/" + customer.getCustomerID();
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        System.out.println("Deleted Customer: " + customer.getCustomerID());

        // Verify deletion
        ResponseEntity<Customer> readResponse = restTemplate.getForEntity(url, Customer.class);
        assertEquals(HttpStatus.NOT_FOUND, readResponse.getStatusCode());
    }

    @Test
    @Order(5)
    void getAll() {
        ResponseEntity<Customer[]> response = restTemplate.getForEntity(baseUrl, Customer[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length >= 0);
        System.out.println("All Customers: " + List.of(response.getBody()));
    }

}