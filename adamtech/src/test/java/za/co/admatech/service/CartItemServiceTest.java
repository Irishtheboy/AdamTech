/*





CartItemServiceTest.java



Author: Teyana Raubenheimer (230237622)



Date: 24 May 2025 */ package za.co.admatech.service;

import org.junit.jupiter.api.*; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.context.SpringBootTest; import za.co.admatech.domain.Cart; import za.co.admatech.domain.CartItem; import za.co.admatech.domain.Customer; import za.co.admatech.domain.Product; import za.co.admatech.domain.enums.ProductCategory; import za.co.admatech.domain.enums.ProductType; import za.co.admatech.factory.AddressFactory; import za.co.admatech.factory.CartFactory; import za.co.admatech.factory.CartItemFactory; import za.co.admatech.factory.CustomerFactory; import za.co.admatech.factory.ProductFactory; import za.co.admatech.repository.CartRepository; import za.co.admatech.repository.CustomerRepository; import za.co.admatech.repository.ProductRepository; import za.co.admatech.service.cart_item_domain_service.ICartItemService;

import jakarta.transaction.Transactional; import java.math.BigDecimal; import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest @TestMethodOrder(MethodOrderer.OrderAnnotation.class) @Transactional class CartItemServiceTest { @Autowired private ICartItemService service;

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
        product = ProductFactory.createProduct(
                124L,
                "Test Product",
                "Description",
                new za.co.admatech.domain.Money(1000, "ZAR"),
                ProductCategory.GAMING,
                ProductType.PERIPHERAL
        );
        customer = CustomerFactory.createCustomer(
                125L,
                "John",
                "Doe",
                "john.doe@example.com",
                "1234567890",
                null, // Will be set after cart creation
                List.of(AddressFactory.createAddress(
                        126L,
                        (short) 12,
                        "Main Street",
                        "Suburb",
                        "City",
                        "Province",
                        (short) 1234
                )),
                List.of()
        );
        cart = CartFactory.createCart(
                127L,
                customer,
                List.of()
        );
        cartItem = CartItemFactory.createCartItem(
                128L,
                product,
                26,
                cart
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
        assertEquals(cartItem.getCartItemID(), created.getCartItemID());
        assertEquals(cartItem.getQuantity(), created.getQuantity());
        System.out.println("Created CartItem: " + created);

        // Update cartItem for subsequent tests
        cartItem = created;
    }

    @Test
    @Order(2)
    void read() {
        CartItem read = service.read(cartItem.getCartItemID());
        assertNotNull(read);
        assertEquals(cartItem.getCartItemID(), read.getCartItemID());
        assertEquals(cartItem.getQuantity(), read.getQuantity());
        System.out.println("Read CartItem: " + read);
    }

    @Test
    @Order(3)
    void update() {
        CartItem updatedItem = new CartItem.Builder()
                .copy(cartItem)
                .setQuantity(30)
                .build();
        CartItem updated = service.update(updatedItem);
        assertNotNull(updated);
        assertEquals(cartItem.getCartItemID(), updated.getCartItemID());
        assertEquals(30, updated.getQuantity());
        System.out.println("Updated CartItem: " + updated);
    }

    @Test
    @Order(4)
    void delete() {
        assertDoesNotThrow(() -> service.delete(cartItem.getCartItemID()));
        assertNull(service.read(cartItem.getCartItemID()));
        System.out.println("Deleted CartItem: " + cartItem.getCartItemID());
    }

    @Test
    @Order(5)
    void getAll() {
        List<CartItem> items = service.getAll();
        assertNotNull(items);
        assertTrue(items.size() >= 0);
        System.out.println("All CartItems: " + items);
    }

}