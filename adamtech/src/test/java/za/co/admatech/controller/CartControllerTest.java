package za.co.admatech.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import za.co.admatech.domain.*;
import za.co.admatech.service.CustomerService;
import za.co.admatech.service.ProductService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    private static final String BASE_URL = "/cart";

    private Cart cart;
    private Customer customer;
    private Product product;

    @BeforeEach
    void init() {
        // Persist Customer
        Address address = new Address.Builder()
                .setStreetNumber((short)12)
                .setStreetName("Oak Street")
                .setSuburb("Parklands")
                .setCity("Cape Town")
                .setProvince("Western Cape")
                .setPostalCode((short)7441)
                .build();

        customer = new Customer.Builder()
                .setFirstName("Test")
                .setLastName("User")
                .setEmail("test@admatech.co.za") // this is the ID
                .setAddress(address)
                .setPhoneNumber("1234567890")
                .setPassword("test123")
                .build();
        customer = customerService.create(customer); // persist

        // Persist Product
        product = new Product.Builder()
                .setName("Test Product")
                .setDescription("A test product")
                .setSku("TEST123")
                .setPrice(new Money.Builder().setAmount(15000).setCurrency("ZAR").build())
                .setCategoryId("CAT1")
                .build();
        product = productService.create(product); // persist, now has productId

        // Create CartItem with persisted product
        CartItem cartItem = new CartItem.Builder()
                .setProduct(product)
                .setQuantity(3)
                .build();

        // Create Cart with persisted customer
        cart = new Cart.Builder()
                .setCustomer(customer)
                .setCartItems(Collections.singletonList(cartItem))
                .build(); // ✅ cartId is null

        // Persist Cart via REST
        ResponseEntity<Cart> response = restTemplate.postForEntity(BASE_URL + "/create", cart, Cart.class);

        if(response.getStatusCode().is2xxSuccessful()) {
            cart = response.getBody();
        } else {
            System.err.println("Cart creation failed: " + response.getStatusCode());
            if(response.hasBody()) System.err.println(response.getBody());
        }

        assertNotNull(cart, "Cart creation failed, received null");
        assertNotNull(cart.getCartId(), "Cart ID should not be null after creation");
    }



    @Test
    @Order(1)
    void createCart() {
        assertNotNull(cart);
        assertNotNull(cart.getCartId());
        assertEquals(customer.getEmail(), cart.getCustomer().getEmail());
        assertEquals(1, cart.getCartItems().size());
        assertEquals(product.getProductId(), cart.getCartItems().get(0).getProduct().getProductId());
        assertEquals("", cart.getCartItems().get(0).getQuantity());
        System.out.println("Created Cart: " + cart);
    }

    @Test
    @Order(2)
    void readCart() {
        ResponseEntity<Cart> response = restTemplate.getForEntity(BASE_URL + "/read/" + cart.getCartId(), Cart.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Cart readCart = response.getBody();
        assertNotNull(readCart);
        assertEquals(cart.getCartId(), readCart.getCartId());
        assertEquals(cart.getCustomer().getEmail(), readCart.getCustomer().getEmail());
        assertEquals(1, readCart.getCartItems().size());
        System.out.println("Read Cart: " + readCart);
    }

    @Test
    @Order(3)
    void updateCart() {
        // --- Update CartItem quantity using Builder ---
        CartItem updatedItem = new CartItem.Builder()
                .copy(cart.getCartItems().get(0))
                .setQuantity(5)
                .build();

        Cart updatedCart = new Cart.Builder()
                .copy(cart)
                .setCartItems(Collections.singletonList(updatedItem))
                .build();

        HttpEntity<Cart> request = new HttpEntity<>(updatedCart);
        ResponseEntity<Cart> response = restTemplate.exchange(BASE_URL + "/update", HttpMethod.PUT, request, Cart.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Cart cartResponse = response.getBody();
        assertNotNull(cartResponse);
        assertEquals(5, cartResponse.getCartItems().get(0).getQuantity());
        System.out.println("Updated Cart: " + cartResponse);

        cart = cartResponse; // update reference
    }

    @Test
    @Order(4)
    @Disabled
    void deleteCart() {
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(BASE_URL + "/delete/" + cart.getCartId(), HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());

        ResponseEntity<Cart> response = restTemplate.getForEntity(BASE_URL + "/read/" + cart.getCartId(), Cart.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        System.out.println("Deleted Cart ID: " + cart.getCartId());
    }

    @Test
    @Order(5)
    void getAllCarts() {
        // --- Create another cart to test getAll ---
        CartItem anotherItem = new CartItem.Builder()
                .setProduct(product)
                .setQuantity(2)
                .build();

        Cart anotherCart = new Cart.Builder()
                .setCustomer(customer)
                .setCartItems(Collections.singletonList(anotherItem))
                .build();

        restTemplate.postForEntity(BASE_URL + "/create", anotherCart, Cart.class);

        ResponseEntity<Cart[]> response = restTemplate.getForEntity(BASE_URL + "/getAll", Cart[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length >= 1);

        System.out.println("All Carts:");
        for (Cart c : response.getBody()) {
            System.out.println(c);
        }
    }

    @Test
    @Order(6)
    void getCartByCustomerEmail() {
        ResponseEntity<Cart> response = restTemplate.getForEntity(BASE_URL + "/customer/" + customer.getEmail(), Cart.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(customer.getEmail(), response.getBody().getCustomer().getEmail());

        System.out.println("Cart by Customer Email: " + response.getBody());
    }
}
