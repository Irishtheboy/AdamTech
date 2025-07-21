/*





CartServiceTest.java



Author: Teyana Raubenheimer (230237622)



Date: 25 May 2025 */ package za.co.admatech.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.*;
import za.co.admatech.domain.enums.OrderStatus;
import za.co.admatech.factory.AddressFactory;
import za.co.admatech.factory.CartFactory;
import za.co.admatech.factory.CustomerFactory;
import za.co.admatech.factory.OrderFactory;
import za.co.admatech.repository.AddressRepository;
import za.co.admatech.repository.CustomerRepository;
import za.co.admatech.service.cart_domain_service.ICartService;

import jakarta.transaction.Transactional; import java.math.BigDecimal; import java.time.LocalDate; import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest @TestMethodOrder(MethodOrderer.OrderAnnotation.class) @Transactional class CartServiceTest { @Autowired private ICartService service;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    private static Cart cart;
    private static Customer customer;
    private static Address address;

    @BeforeAll
    public static void setUp() {
        address = AddressFactory.createAddress(
                2L,
                (short) 101,
                "Tech Avenue",
                "Cape Town",
                "Western Cape",
                "Limpopo",
                (short)434
        );
        customer = CustomerFactory.createCustomer(
                123L,
                "Rorisang",
                "Makgana",
                "rorisang@example.com",
                "0821234567",
                null, // Will be set after cart creation
                List.of(address),
                List.of()
        );
        cart = CartFactory.createCart(
                1L,
                customer,
                List.of()
        );
    }

    @Test
    @Order(1)
    void create() {
        addressRepository.save(address);
        customerRepository.save(customer);
        Cart created = service.create(cart);
        assertNotNull(created);
        assertEquals(cart.getCartID(), created.getCartID());
        System.out.println("Created Cart: " + created);

        // Update cart for subsequent tests
        cart = created;
    }

    @Test
    @Order(2)
    void read() {
        Cart read = service.read(cart.getCartID());
        assertNotNull(read);
        assertEquals(cart.getCartID(), read.getCartID());
        System.out.println("Read Cart: " + read);
    }

    @Test
    void update() {
        Cart updatedCart = new Cart.Builder()
                .copy(cart)
                .setCustomer(customer)
                .build();
        Cart updated = service.update(updatedCart);
        assertNotNull(updated);
        assertEquals(cart.getCartID(), updated.getCartID());
        System.out.println("Updated Cart: " + updated);
    }

    @Test
    @Order(4)
    void delete() {
        assertDoesNotThrow(() -> service.delete(cart.getCartID()));
        assertNull(service.read(cart.getCartID()));
        System.out.println("Deleted Cart: " + cart.getCartID());
    }

    @Test
    @Order(5)
    void getAll() {
        List<Cart> carts = service.getAll();
        assertNotNull(carts);
        assertTrue(carts.size() >= 0);
        System.out.println("All Carts: " + carts);
    }

}