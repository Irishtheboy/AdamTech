/*
 * CartControllerTest.java
 * Author: Teyana Raubenheimer (230237622)
 * Date: 31 May 2025
 */
package za.co.admatech.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import za.co.admatech.domain.Address;
import za.co.admatech.domain.Cart;
import za.co.admatech.domain.CartItem;
import za.co.admatech.domain.Customer;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Product;
import za.co.admatech.service.CustomerService;
import za.co.admatech.service.ProductService;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
class CartControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    private static final String BASE_URL = "/admatech/cart";

    private static Cart cart;
    private Customer customer;
    private Product product;
    private CartItem cartItem;

    @BeforeAll
    static void setUp() {
        // Initialize in @BeforeEach after persisting dependencies
    }

    @BeforeEach
    void beforeEach() {
        // Initialize Address
        Address address = new Address.Builder()
                .setStreetNumber((short) 12)
                .setStreetName("Oak Street")
                .setSuburb("Parklands")
                .setCity("Cape Town")
                .setProvince("Western Cape")
                .setPostalCode((short) 7441)
                .build();

        // Initialize Customer
        customer = new Customer.Builder()
                .setFirstName("Test")
                .setLastName("User")
                .setEmail("test@admatech.co.za")
                .setAddress(address)
                .setPhoneNumber("1234567890")
                .build();
        customer = customerService.create(customer);

        // Initialize Product
        product = new Product.Builder()
                .setName("Test Product")
                .setDescription("A test product")
                .setSku("TEST123")
                .setPrice(new Money.Builder().setAmount(15000).setCurrency("ZAR").build()) // 150.00 ZAR
                .setCategoryId("CAT1")
                .build();
        product = productService.create(product);

        // Initialize CartItem
        cartItem = new CartItem.Builder()
                .setProduct(product)
                .setQuantity(3)
                .build();

        // Initialize Cart
        cart = new Cart.Builder()
                .setCustomer(customer)
                .setCartItems(Collections.singletonList(cartItem))
                .build();
    }

    @Test
    @Order(1)
    void create() {
        // Set Cart reference in CartItem
        cartItem = new CartItem.Builder()
                .setCart(cart)
                .setProduct(product)
                .setQuantity(3)
                .build();
        cart.getCartItems().set(0, cartItem);

        ResponseEntity<Cart> postResponse = restTemplate.postForEntity(BASE_URL + "/create", cart, Cart.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        assertNotNull(postResponse.getBody());
        assertNotNull(postResponse.getBody().getCartId());
        assertEquals(customer.getCustomerId(), postResponse.getBody().getCustomer().getCustomerId());
        assertEquals(1, postResponse.getBody().getCartItems().size());
        assertEquals(product.getProductId(), postResponse.getBody().getCartItems().get(0).getProduct().getProductId());
        assertEquals(3, postResponse.getBody().getCartItems().get(0).getQuantity());

        cart = postResponse.getBody(); // Update reference
        System.out.println("Created: " + cart);
    }

    @Test
    @Order(2)
    void read() {
        ResponseEntity<Cart> response = restTemplate.getForEntity(BASE_URL + "/read/" + cart.getCartId(), Cart.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(cart.getCartId(), response.getBody().getCartId());
        assertEquals(cart.getCustomer().getCustomerId(), response.getBody().getCustomer().getCustomerId());
        assertEquals(1, response.getBody().getCartItems().size());
        assertEquals(product.getProductId(), response.getBody().getCartItems().get(0).getProduct().getProductId());

        System.out.println("Read: " + response.getBody());
    }

    @Test
    @Order(3)
    void update() {
        Cart updatedCart = new Cart.Builder()
                .copy(cart)
                .setCartItems(Collections.singletonList(new CartItem.Builder()
                        .setCart(cart)
                        .setProduct(product)
                        .setQuantity(5) // Updated quantity
                        .build()))
                .build();

        HttpEntity<Cart> request = new HttpEntity<>(updatedCart);
        ResponseEntity<Cart> response = restTemplate.exchange(BASE_URL + "/update/" + cart.getCartId(), HttpMethod.PUT, request, Cart.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(cart.getCartId(), response.getBody().getCartId());
        assertEquals(cart.getCustomer().getCustomerId(), response.getBody().getCustomer().getCustomerId());
        assertEquals(1, response.getBody().getCartItems().size());
        assertEquals(5, response.getBody().getCartItems().get(0).getQuantity());

        cart = response.getBody(); // Update reference
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    @Order(4)
    void delete() {
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(BASE_URL + "/delete/" + cart.getCartId(), HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());

        ResponseEntity<Cart> response = restTemplate.getForEntity(BASE_URL + "/read/" + cart.getCartId(), Cart.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        System.out.println("Deleted: ID " + cart.getCartId());
    }

    @Test
    @Order(5)
    void getAll() {
        // Create a new cart to ensure the list is non-empty
        Cart newCart = new Cart.Builder()
                .setCustomer(customer)
                .setCartItems(Collections.singletonList(new CartItem.Builder()
                        .setProduct(product)
                        .setQuantity(2)
                        .build()))
                .build();
        restTemplate.postForEntity(BASE_URL + "/create", newCart, Cart.class);

        ResponseEntity<Cart[]> response = restTemplate.getForEntity(BASE_URL + "/getAll", Cart[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);

        System.out.println("Get All:");
        for (Cart c : response.getBody()) {
            System.out.println(c);
        }
    }
}