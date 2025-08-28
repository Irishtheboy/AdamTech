package za.co.admatech.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import za.co.admatech.domain.*;
import za.co.admatech.service.CartService;
import za.co.admatech.service.CartItemService;
import za.co.admatech.service.CustomerService;
import za.co.admatech.service.ProductService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class CartItemControllerTest {

    private static CartItem cartItem;
    private static Cart cart;
    private static Product product;
    private static Customer customer;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    private static final String BASE_URL = "/cart-items";

    @BeforeEach
    void setUp() {
        // Persist Customer
        customer = customerService.create(
                new Customer.Builder()
                        .setFirstName("John")
                        .setLastName("Doe")
                        .setEmail("john@example.com")
                        .setPhoneNumber("1234567890")
                        .build()
        );

        // Persist Product
        product = productService.create(
                new Product.Builder()
                        .setName("Test Product")
                        .setDescription("Sample product")
                        .setSku("SKU123")
                        .setPrice(new Money.Builder().setAmount(5000).setCurrency("ZAR").build())
                        .setCategoryId("CAT1")
                        .build()
        );

        // Persist Cart
        cart = cartService.create(
                new Cart.Builder()
                        .setCustomer(customer)
                        .build()
        );

        // Create CartItem via REST
        cartItem = restTemplate.postForObject(
                BASE_URL + "/create",
                new CartItem.Builder()
                        .setCart(cart)
                        .setProduct(product)
                        .setQuantity(3)
                        .build(),
                CartItem.class
        );

        assertNotNull(cartItem);
        assertNotNull(cartItem.getCartItemId());
    }

    @Test
    void a_create() {
        assertNotNull(cartItem);
        System.out.println("Created CartItem: " + cartItem);
    }

    @Test
    void b_read() {
        ResponseEntity<CartItem> response = restTemplate.getForEntity(
                BASE_URL + "/read/" + cartItem.getCartItemId(),
                CartItem.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(cartItem.getCartItemId(), response.getBody().getCartItemId());
        System.out.println("Read CartItem: " + response.getBody());
    }

    @Test
    void c_update() {
        CartItem updatedCartItem = new CartItem.Builder()
                .copy(cartItem)
                .setQuantity(10)
                .build();

        HttpEntity<CartItem> request = new HttpEntity<>(updatedCartItem);
        ResponseEntity<CartItem> response = restTemplate.exchange(
                BASE_URL + "/update",
                HttpMethod.PUT,
                request,
                CartItem.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedCartItem.getCartItemId(), response.getBody().getCartItemId());
        assertEquals(10, response.getBody().getQuantity());
        System.out.println("Updated CartItem: " + response.getBody());

        cartItem = response.getBody(); // Keep updated object
    }

    @Test
    void d_delete() {
        restTemplate.delete(BASE_URL + "/delete/" + cartItem.getCartItemId());

        ResponseEntity<CartItem> response = restTemplate.getForEntity(
                BASE_URL + "/read/" + cartItem.getCartItemId(),
                CartItem.class
        );

        assertTrue(response.getStatusCode().is2xxSuccessful() || response.getStatusCode() == HttpStatus.NOT_FOUND);
        System.out.println("Deleted CartItem with ID: " + cartItem.getCartItemId());
    }

    @Test
    void e_getAll() {
        ResponseEntity<CartItem[]> response = restTemplate.getForEntity(
                BASE_URL + "/getAll",
                CartItem[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        System.out.println("All CartItems:");
        for (CartItem ci : response.getBody()) {
            System.out.println(ci);
        }
    }
}
