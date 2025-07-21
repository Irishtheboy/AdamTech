/*





CartControllerTest.java



Author: Teyana Raubenheimer (230237622)



Date: 31 May 2025 */ package za.co.admatech.controller;

import org.junit.jupiter.api.*; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.context.SpringBootTest; import org.springframework.boot.test.web.server.LocalServerPort; import org.springframework.http.HttpMethod; import org.springframework.http.HttpStatus; import org.springframework.http.ResponseEntity; import za.co.admatech.domain.Cart; import za.co.admatech.domain.Customer; import za.co.admatech.factory.CartFactory; import za.co.admatech.factory.CustomerFactory; import za.co.admatech.repository.CustomerRepository;

import jakarta.transaction.Transactional; import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) @TestMethodOrder(MethodOrderer.OrderAnnotation.class) @Transactional class CartControllerTest { @LocalServerPort private int port;

    @Autowired
    private org.springframework.boot.test.web.client.TestRestTemplate restTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    private String baseUrl;

    private static Cart cart;
    private static Customer customer;

    @BeforeAll
    public static void setUp() {
        customer = CustomerFactory.createCustomer(
                1L,
                "John",
                "Doe",
                "john.doe@example.com",
                "1234567890",
                null, // Will be set after cart creation
                List.of(), // Empty addresses for simplicity
                List.of()  // Empty orders
        );
        cart = CartFactory.createCart(
                1L,
                customer,
                List.of()
        );
    }

    @BeforeEach
    public void init() {
        baseUrl = "http://localhost:" + port + "/carts";
    }

    @Test
    @Order(1)
    void create() {
        customerRepository.save(customer);
        ResponseEntity<Cart> response = restTemplate.postForEntity(baseUrl, cart, Cart.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(cart.getCartID(), response.getBody().getCartID());
        System.out.println("Created Cart: " + response.getBody());

        // Update cart for subsequent tests
        cart = response.getBody();
    }

    @Test
    @Order(2)
    void read() {
        String url = baseUrl + "/" + cart.getCartID();
        ResponseEntity<Cart> response = restTemplate.getForEntity(url, Cart.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(cart.getCartID(), response.getBody().getCartID());
        System.out.println("Read Cart: " + response.getBody());
    }

    @Test
    @Order(3)
    void update() {
        Cart updatedCart = new Cart.Builder()
                .copy(cart)
                .setCustomer(customer)
                .build();
        ResponseEntity<Cart> response = restTemplate.exchange(baseUrl, HttpMethod.PUT, new org.springframework.http.HttpEntity<>(updatedCart), Cart.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(cart.getCartID(), response.getBody().getCartID());
        System.out.println("Updated Cart: " + response.getBody());
    }

    @Test
    @Order(4)
    void delete() {
        String url = baseUrl + "/" + cart.getCartID();
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        System.out.println("Deleted Cart: " + cart.getCartID());

        // Verify deletion
        ResponseEntity<Cart> readResponse = restTemplate.getForEntity(url, Cart.class);
        assertEquals(HttpStatus.NOT_FOUND, readResponse.getStatusCode());
    }

    @Test
    @Order(5)
    void getAll() {
        ResponseEntity<Cart[]> response = restTemplate.getForEntity(baseUrl, Cart[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length >= 0);
        System.out.println("All Carts: " + List.of(response.getBody()));
    }

}