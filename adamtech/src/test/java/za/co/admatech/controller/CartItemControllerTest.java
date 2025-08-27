/*
 * CartItemControllerTest.java
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
import za.co.admatech.service.CartService;
import za.co.admatech.service.CustomerService;
import za.co.admatech.service.ProductService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
class CartItemControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    private static CartItem cartItem;
    private static Cart cart;
    private static Product product;
    private static Customer customer;

    private static final String BASE_URL = "/adamtech/cart-items";

    @BeforeAll
    static void setUp() {
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

        // Initialize Product
        product = new Product.Builder()
                .setName("Test Product")
                .setDescription("A test product")
                .setSku("TEST123")
                .setPrice(new Money.Builder().setAmount(10000).setCurrency("ZAR").build()) // 100.00 ZAR
                .setCategoryId("CAT1")
                .build();

        // Initialize Cart
        cart = new Cart.Builder()
                .setCustomer(customer)
                .build();

        // Initialize CartItem
        cartItem = new CartItem.Builder()
                .setProduct(product)
                .setQuantity(20)
                .setCart(cart)
                .build();
    }

    @BeforeEach
    void beforeEach() {
        // Persist dependencies before each test
        customer = customerService.create(customer);
        product = productService.create(product);
        cart = cartService.create(cart);
        cartItem = new CartItem.Builder()
                .setProduct(product)
                .setQuantity(20)
                .setCart(cart)
                .build();
    }

    @Test
    @Order(1)
    void create() {
        String url = BASE_URL + "/create";
        ResponseEntity<CartItem> postResponse = restTemplate.postForEntity(url, cartItem, CartItem.class);
        assertNotNull(postResponse);
//        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        assertNotNull(postResponse.getBody());
        assertNotNull(postResponse.getBody().getCartItemId());
        assertEquals(cartItem.getQuantity(), postResponse.getBody().getQuantity());
        assertEquals(cartItem.getProduct().getProductId(), postResponse.getBody().getProduct().getProductId());
        assertEquals(cartItem.getCart().getCartId(), postResponse.getBody().getCart().getCartId());

        cartItem = postResponse.getBody(); // Update with persisted object
        System.out.println("Created: " + cartItem);
    }

    @Test
    @Order(2)
    void read() {
        String url = BASE_URL + "/read/" + cartItem.getCartItemId();
        ResponseEntity<CartItem> response = restTemplate.getForEntity(url, CartItem.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(cartItem.getCartItemId(), response.getBody().getCartItemId());
        assertEquals(cartItem.getQuantity(), response.getBody().getQuantity());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    @Order(3)
    void update() {
        CartItem updatedCartItem = new CartItem.Builder()
                .copy(cartItem)
                .setQuantity(30)
                .build();

        String url = BASE_URL + "/update/" + cartItem.getCartItemId();
        HttpEntity<CartItem> request = new HttpEntity<>(updatedCartItem);
        ResponseEntity<CartItem> response = restTemplate.exchange(url, HttpMethod.PUT, request, CartItem.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(cartItem.getCartItemId(), response.getBody().getCartItemId());
        assertEquals(30, response.getBody().getQuantity());
        assertEquals(cartItem.getProduct().getProductId(), response.getBody().getProduct().getProductId());

        cartItem = response.getBody(); // Update with persisted object
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    @Order(4)
    void delete() {
        String url = BASE_URL + "/delete/" + cartItem.getCartItemId();
        restTemplate.delete(url);

        ResponseEntity<CartItem> response = restTemplate.getForEntity(BASE_URL + "/read/" + cartItem.getCartItemId(), CartItem.class);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("Deleted: ID " + cartItem.getCartItemId());
    }

    @Test
    @Order(5)
    void getAll() {
        // Create a new CartItem to ensure the list is non-empty
        CartItem newCartItem = new CartItem.Builder()
                .setProduct(product)
                .setQuantity(10)
                .setCart(cart)
                .build();
        restTemplate.postForEntity(BASE_URL + "/create", newCartItem, CartItem.class);

        String url = BASE_URL + "/getAll";
        ResponseEntity<CartItem[]> response = restTemplate.getForEntity(url, CartItem[].class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        System.out.println("Get All:");
        for (CartItem ci : response.getBody()) {
            System.out.println(ci);
        }
    }
}