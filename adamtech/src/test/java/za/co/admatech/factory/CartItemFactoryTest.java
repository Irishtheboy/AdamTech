/*
 * CartItemFactoryTest.java
 * Author: Teyana Raubenheimer (230237622)
 * Date: 18 May 2025
 */
package za.co.admatech.factory;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class CartItemFactoryTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    private Customer customer;
    private Cart cart;
    private Product product;
    private CartItem cartItem;

    @BeforeAll
    void setup() {
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
    }

    @BeforeEach
    void beforeEach() {
        // Persist dependencies before each test
        customer = customerService.create(customer);
        product = productService.create(product);
        cart = cartService.create(cart);

        // Create CartItem using Builder (or factory)
        cartItem = new CartItem.Builder()
                .setProduct(product)
                .setQuantity(2)
                .setCart(cart)
                .build();
    }

    @Test
    @Order(1)
    void createCartItem() {
        assertNotNull(cartItem);
        assertNotNull(cartItem.getProduct());
        assertNotNull(cartItem.getCart());
        assertEquals(2, cartItem.getQuantity());
        assertEquals(product.getProductId(), cartItem.getProduct().getProductId());
        assertEquals(cart.getCartId(), cartItem.getCart().getCartId());
        System.out.println("Created CartItem: " + cartItem);
    }

    @Test
    @Order(2)
    void createCartItemWithInvalidData() {
        // Test creating CartItem with null Product
        CartItem invalidCartItem = new CartItem.Builder()
                .setQuantity(2)
                .setCart(cart)
                .build(); // Missing Product
        assertNotNull(invalidCartItem); // Builder allows null Product, but persistence would fail
        assertNull(invalidCartItem.getProduct());
        System.out.println("Created Invalid CartItem: " + invalidCartItem);
    }
}