package za.co.admatech.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.*;
import za.co.admatech.repository.CartRepository;
import za.co.admatech.repository.CustomerRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer testCustomer;
    private Cart testCart;

    @BeforeEach
    void setUp() {
        // Only clear carts to avoid FK issues
        cartRepository.deleteAll();

        // Build Address
        Address address = new Address.Builder()
                .setStreetNumber((short) 45)
                .setStreetName("High Street")
                .setSuburb("Town Centre")
                .setCity("Johannesburg")
                .setProvince("Gauteng")
                .setPostalCode((short) 2000)
                .build();

        // Persist test Customer
        testCustomer = customerRepository.save(
                new Customer.Builder()
                        .setFirstName("Alice")
                        .setLastName("Smith")
                        .setEmail("alice.smith@example.com")
                        .setPhoneNumber("0712345678")
                        
                        .setAddress(address)
                        .build()
        );
        // Build Cart
        testCart = new Cart.Builder()
                .setCustomer(testCustomer)
                .build();
    }

    @Test
    @Order(1)
    void testCreateCart() {
        Cart created = cartService.create(testCart);
        assertNotNull(created);
        assertNotNull(created.getCartId());
        assertEquals("Alice", created.getCustomer().getFirstName());
        assertEquals(1, created.getCartItems().size());
    }

    @Test
    @Order(2)
    void testReadCart() {
        Cart created = cartService.create(testCart);
        Cart found = cartService.read(created.getCartId());

        assertNotNull(found);
        assertEquals("alice.smith@example.com", found.getCustomer().getEmail());
    }

    @Test
    @Order(3)
    void testUpdateCart() {
        Cart created = cartService.create(testCart);

        Cart updatedCart = new Cart.Builder()
                .copy(created)
                .build();

        Cart updated = cartService.update(updatedCart);
        assertEquals(2, updated.getCartItems().size());
    }

    @Test
    @Order(4)
    @Disabled("Delete can be enabled once safe")
    void testDeleteCart() {
        Cart created = cartService.create(testCart);
        boolean deleted = cartService.delete(created.getCartId());
        assertTrue(deleted);
        assertNull(cartService.read(created.getCartId()));
    }
}
