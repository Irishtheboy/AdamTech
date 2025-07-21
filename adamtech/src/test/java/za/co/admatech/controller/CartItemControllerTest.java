/*





CartItemControllerTest.java



Author: Teyana Raubenheimer (230237622)



Date: 31 May 2025 */ package za.co.admatech.controller;

import org.junit.jupiter.api.*; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.context.SpringBootTest; import org.springframework.boot.test.web.server.LocalServerPort; import org.springframework.http.HttpMethod; import org.springframework.http.HttpStatus; import org.springframework.http.ResponseEntity; import za.co.admatech.domain.Cart; import za.co.admatech.domain.CartItem; import za.co.admatech.domain.Product; import za.co.admatech.domain.enums.ProductCategory; import za.co.admatech.domain.enums.ProductType; import za.co.admatech.factory.CartFactory; import za.co.admatech.factory.CartItemFactory; import za.co.admatech.factory.CustomerFactory; import za.co.admatech.factory.ProductFactory; import za.co.admatech.repository.CartRepository; import za.co.admatech.repository.ProductRepository;

import jakarta.transaction.Transactional; import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) @TestMethodOrder(MethodOrderer.OrderAnnotation.class) @Transactional class CartItemControllerTest { @LocalServerPort private int port;

    @Autowired
    private org.springframework.boot.test.web.client.TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    private String baseUrl;

    private static CartItem cartItem;
    private static Cart cart;
    private static Product product;

    @BeforeAll
    public static void setUp() {
        product = ProductFactory.createProduct(
                124L,
                "Test Product",
                "Description",
                new za.co.admatech.domain.Money(100, "USD"),
                ProductCategory.GAMING,
                ProductType.PERIPHERAL
        );
        cart = CartFactory.createCart(
                125L,
                CustomerFactory.createCustomer(
                        126L,
                        "John",
                        "Doe",
                        "john.doe@example.com",
                        "1234567890",
                        null,
                        List.of(),
                        List.of()
                ),
                List.of()
        );
        cartItem = CartItemFactory.createCartItem(
                124L,
                product,
                26,
                cart
        );
    }

    @BeforeEach
    public void init() {
        baseUrl = "http://localhost:" + port + "/cart-items";
    }

    @Test
    @Order(1)
    void create() {
        productRepository.save(product);
        cartRepository.save(cart);
        ResponseEntity<CartItem> response = restTemplate.postForEntity(baseUrl, cartItem, CartItem.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(cartItem.getCartItemID(), response.getBody().getCartItemID());
        assertEquals(cartItem.getQuantity(), response.getBody().getQuantity());
        System.out.println("Created CartItem: " + response.getBody());

        // Update cartItem for subsequent tests
        cartItem = response.getBody();
    }

    @Test
    @Order(2)
    void read() {
        String url = baseUrl + "/" + cartItem.getCartItemID();
        ResponseEntity<CartItem> response = restTemplate.getForEntity(url, CartItem.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(cartItem.getCartItemID(), response.getBody().getCartItemID());
        assertEquals(cartItem.getQuantity(), response.getBody().getQuantity());
        System.out.println("Read CartItem: " + response.getBody());
    }

    @Test
    @Order(3)
    void update() {
        CartItem updatedCartItem = new CartItem.Builder()
                .copy(cartItem)
                .setQuantity(30)
                .build();
        ResponseEntity<CartItem> response = restTemplate.exchange(baseUrl, HttpMethod.PUT, new org.springframework.http.HttpEntity<>(updatedCartItem), CartItem.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(cartItem.getCartItemID(), response.getBody().getCartItemID());
        assertEquals(30, response.getBody().getQuantity());
        System.out.println("Updated CartItem: " + response.getBody());
    }

    @Test
    @Order(4)
    void delete() {
        String url = baseUrl + "/" + cartItem.getCartItemID();
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        System.out.println("Deleted CartItem: " + cartItem.getCartItemID());

        // Verify deletion
        ResponseEntity<CartItem> readResponse = restTemplate.getForEntity(url, CartItem.class);
        assertEquals(HttpStatus.NOT_FOUND, readResponse.getStatusCode());
    }

    @Test
    @Order(5)
    void getAll() {
        ResponseEntity<CartItem[]> response = restTemplate.getForEntity(baseUrl, CartItem[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length >= 0);
        System.out.println("All CartItems: " + List.of(response.getBody()));
    }

}