package za.co.admatech.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import za.co.admatech.config.TestSecurityConfig;
import za.co.admatech.domain.*;
import za.co.admatech.domain.Order;
import za.co.admatech.domain.enums.OrderStatus;
import za.co.admatech.repository.CustomerRepository;
import za.co.admatech.repository.OrderRepository;
import za.co.admatech.repository.ProductRepository;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(TestSecurityConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository; // needed to save product

    private Customer testCustomer;
    private Product testProduct;
    private Order testOrder;

    @BeforeEach
    void setUp() {
        // Only clean orders and customers, not products
        orderRepository.deleteAll();
        customerRepository.deleteAll();

        // Create a test customer
        testCustomer = new Customer.Builder()
                .setFirstName("Jane")
                .setLastName("Doe")
                .setEmail("jane.doe@example.com")
                .setPhoneNumber("0821234567")
                .setAddress(
                        new Address.Builder()
                                .setStreetNumber((short)12)
                                .setStreetName("High St")
                                .setSuburb("Central")
                                .setCity("Johannesburg")
                                .setProvince("Gauteng")
                                .setPostalCode((short)2000)
                                .build()
                )
                .build();
        customerRepository.save(testCustomer);

        // Create a test product (save it to avoid transient FK issues)
        testProduct = new Product.Builder()
                .setName("Laptop")
                .setDescription("Gaming Laptop")
                .setSku("LAP123")
                .setPrice(new Money.Builder().setAmount(10000).setCurrency("ZAR").build())
                .setCategoryId("ELEC")
                .build();
        productRepository.save(testProduct);

        // Create order items
        OrderItem orderItem = new OrderItem.Builder()
                .setProduct(testProduct)
                .setQuantity(2)
                .setUnitPrice(testProduct.getPrice())
                .build();

        // Create a test order
        testOrder = new Order.Builder()
                .setCustomer(testCustomer)
                .setOrderDate(LocalDate.now())
                .setOrderStatus(OrderStatus.PENDING)
                .setTotalAmount(testProduct.getPrice()) // or sum of order items
                .setOrderItems(Arrays.asList(orderItem))
                .build();
    }

    @Test
    void testCreate() {
        Order created = orderService.create(testOrder);
        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals(OrderStatus.PENDING, created.getOrderStatus());
    }

    @Test
    void testRead() {
        Order created = orderService.create(testOrder);
        Order found = orderService.read(created.getId());
        assertNotNull(found);
        assertEquals(testCustomer.getEmail(), found.getCustomer().getEmail());
    }

    @Test
    void testUpdate() {
        Order created = orderService.create(testOrder);

        Order updatedOrder = new Order.Builder()
                .copy(created)
                .setOrderStatus(OrderStatus.CONFIRMED)
                .build();

        Order updated = orderService.update(updatedOrder);
        assertEquals(OrderStatus.CONFIRMED, updated.getOrderStatus());
    }

    @Test
    void testDelete() {
        Order created = orderService.create(testOrder);
        boolean deleted = orderService.delete(created.getId());
        assertTrue(deleted);
        assertNull(orderService.read(created.getId()));
    }
}
