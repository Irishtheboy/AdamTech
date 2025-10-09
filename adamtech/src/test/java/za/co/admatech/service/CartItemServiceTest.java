/*
 * CartItemServiceTest.java
 * Author: Teyana Raubenheimer (230237622)
 * Date: 24 May 2025
 */
package za.co.admatech.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Address;
import za.co.admatech.domain.Cart;
import za.co.admatech.domain.CartItem;
import za.co.admatech.domain.Customer;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Product;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartItemServiceTest {

    @Autowired
    private ICartItemService service;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    private static CartItem savedCartItem;
    private static Cart cart;
    private static Product product;
    private static Customer customer;

    @BeforeAll
    static void setup() {
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

    @Test
    @Order(1)
    void create() {
        // Persist dependencies
        customer = customerService.create(customer);
        product = productService.create(product);
        cart = cartService.create(cart);

        CartItem newItem = new CartItem.Builder()
                .setProduct(product)
                .setQuantity(10)
                .setCart(cart)
                .build();

        savedCartItem = service.create(newItem);
        assertNotNull(savedCartItem);
        assertNotNull(savedCartItem.getCartItemId());
        assertEquals(10, savedCartItem.getQuantity());
        assertEquals(product.getProductId(), savedCartItem.getProduct().getProductId());
        assertEquals(cart.getCartId(), savedCartItem.getCart().getCartId());
        System.out.println("Created: " + savedCartItem);
    }

    @Test
    @Order(2)
    void read() {
        assertNotNull(savedCartItem);
        CartItem readItem = service.read(savedCartItem.getCartItemId());
        assertNotNull(readItem);
        assertEquals(savedCartItem.getCartItemId(), readItem.getCartItemId());
        assertEquals(savedCartItem.getQuantity(), readItem.getQuantity());
        assertEquals(savedCartItem.getProduct().getProductId(), readItem.getProduct().getProductId());
        System.out.println("Read: " + readItem);
    }

    @Test
    @Order(3)
    void update() {
        assertNotNull(savedCartItem);
        CartItem updatedItem = new CartItem.Builder()
                .copy(savedCartItem)
                .setQuantity(3)
                .build();

        CartItem updated = service.update(updatedItem);
        assertNotNull(updated);
        assertEquals(savedCartItem.getCartItemId(), updated.getCartItemId());
        assertEquals(3, updated.getQuantity());
        assertEquals(savedCartItem.getProduct().getProductId(), updated.getProduct().getProductId());
        System.out.println("Updated: " + updated);
    }

    @Test
    @Order(4)
    @Disabled
    void delete() {
        assertNotNull(savedCartItem);
        boolean deleted = service.delete(savedCartItem.getCartItemId());
        assertTrue(deleted);

        // Verify deletion
        CartItem readItem = service.read(savedCartItem.getCartItemId());
        assertNull(readItem);
        System.out.println("Deleted: ID " + savedCartItem.getCartItemId());
    }

    @Test
    @Order(5)
    void getAll() {
        // Create a new CartItem for getAll test
        CartItem newItem = new CartItem.Builder()
                .setProduct(product)
                .setQuantity(5)
                .setCart(cart)
                .build();
        service.create(newItem);

        List<CartItem> all = service.getAll();
        assertNotNull(all);
        assertTrue(all.size() > 0);
        System.out.println("All CartItems: " + all);
    }
}