/*
CartItemServiceTest.java
Author: Teyana Raubenheimer (230237622)
Date: 24 May 2025
*/
package za.co.admatech.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import za.co.admatech.domain.*;
import za.co.admatech.domain.enums.ProductCategory;
import za.co.admatech.domain.enums.ProductType;
import za.co.admatech.factory.*;
import za.co.admatech.repository.CartRepository;
import za.co.admatech.repository.CustomerRepository;
import za.co.admatech.repository.ProductRepository;
import za.co.admatech.service.cart_item_domain_service.ICartItemService;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
class CartItemServiceTest {

    @Autowired
    private ICartItemService service;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private static CartItem cartItem;
    private static Cart cart;
    private static Product product;
    private static Customer customer;

    @BeforeAll
    public static void setUp() {
        Category category = new Category.Builder()
                .categoryId("cat-101")
                .parentCategoryId("root")
                .name("Gaming Peripherals")
                .build();

        product = ProductFactory.createProduct(
                "prod-101",
                "Mechanical Keyboard",
                "RGB Gaming Keyboard",
                new Money.Builder()
                        .amount(new BigDecimal("1200.00"))
                        .currency("ZAR")
                        .build(),
                category,
                ProductType.PERIPHERAL
        );

        Address address = AddressFactory.createAddress(
                101L,
                "221B",
                "Baker Street",
                "Suburbia",
                "Cape Town",
                "Western Cape",
                "8001"
        );

        customer = CustomerFactory.createCustomer(
                "cust-101",
                "Jane",
                "Smith",
                "jane.smith@example.com",
                address
        );

        cart = CartFactory.createCart(
                "cart-101",
                customer.getCustomerId(),
                List.of(),
                customer
        );

        cartItem = CartItemFactory.createCartItem(
                product.getProductId(),
                5,
                cart,
                product
        );
    }

    @Test
    @Order(1)
    void create() {
        customerRepository.save(customer);
        productRepository.save(product);
        cartRepository.save(cart);
        CartItem created = service.create(cartItem);
        assertNotNull(created);
        assertEquals(cartItem.getId(), created.getId());
        assertEquals(cartItem.getQuantity(), created.getQuantity());
        System.out.println("Created CartItem: " + created);

        cartItem = created; // update for later tests
    }

    @Test
    @Order(2)
    void read() {
        CartItem read = service.read(cartItem.getId());
        assertNotNull(read);
        assertEquals(cartItem.getId(), read.getId());
        assertEquals(cartItem.getQuantity(), read.getQuantity());
        System.out.println("Read CartItem: " + read);
    }

    @Test
    @Order(3)
    void update() {
        // Use cartItem.copy() to create a copy, then modify via Builder
        CartItem updatedItem = cartItem.copy() // Correctly call copy on CartItem
                .getBuilder() // Assuming a method to get Builder (see note below)
                .quantity(10)
                .build();
        CartItem updated = service.update(updatedItem);
        assertNotNull(updated);
        assertEquals(cartItem.getId(), updated.getId());
        assertEquals(10, updated.getQuantity());
        System.out.println("Updated CartItem: " + updated);
    }

    @Test
    @Order(4)
    void delete() {
        assertDoesNotThrow(() -> service.delete(cartItem.getId()));
        assertNull(service.read(cartItem.getId()));
        System.out.println("Deleted CartItem: " + cartItem.getId());
    }

    @Test
    @Order(5)
    void getAll() {
        List<CartItem> items = service.getAll();
        assertNotNull(items);
        assertFalse(items.isEmpty()); // Fixed to check for non-empty list
        System.out.println("All CartItems: " + items);
    }
}