/*
 * OrderFactoryTest.java
 * Author: Naqeebah Khan (219099073)
 * Date: 17 May 2025
 */
package za.co.admatech.factory;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import za.co.admatech.domain.*;
import za.co.admatech.domain.Order;
import za.co.admatech.domain.enums.OrderStatus;
import za.co.admatech.service.CustomerService;
import za.co.admatech.service.OrderService;
import za.co.admatech.service.ProductService;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class OrderFactoryTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    private Customer customer;
    private Product product;
    private OrderItem orderItem;
    private Order order1;
    private Order order2;

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
                .setPrice(new Money.Builder().setAmount(15000).setCurrency("ZAR").build()) // 150.00 ZAR
                .setCategoryId("CAT1")
                .build();

        // Initialize OrderItem
        orderItem = new OrderItem.Builder()
                .setProduct(product)
                .setQuantity(3)
                .build();

        // Initialize Orders
        order1 = new Order.Builder()
                .setCustomer(customer)
                .setOrderDate(LocalDate.of(2025, 5, 5))
                .setOrderStatus(OrderStatus.PENDING)
                .setTotalAmount(new Money.Builder().setAmount(45000).setCurrency("ZAR").build()) // 450.00 ZAR
                .setOrderItems(Collections.singletonList(orderItem))
                .build();

        order2 = new Order.Builder()
                .setCustomer(customer)
                .setOrderDate(LocalDate.of(2025, 6, 6))
                .setOrderStatus(OrderStatus.CONFIRMED)
                .setTotalAmount(new Money.Builder().setAmount(85000).setCurrency("ZAR").build()) // 850.00 ZAR
                .setOrderItems(Collections.singletonList(orderItem))
                .build();
    }

    @BeforeEach
    void beforeEach() {
        // Persist dependencies before each test
        customer = customerService.create(customer);
        product = productService.create(product);

        // Update OrderItem with persisted Order (set later after Order creation)
        orderItem = new OrderItem.Builder()
                .setProduct(product)
                .setQuantity(3)
                .build();

        // Update Orders with persisted Customer and OrderItem
        order1 = new Order.Builder()
                .setCustomer(customer)
                .setOrderDate(LocalDate.of(2025, 5, 5))
                .setOrderStatus(OrderStatus.PENDING)
                .setTotalAmount(new Money.Builder().setAmount(45000).setCurrency("ZAR").build())
                .setOrderItems(Collections.singletonList(orderItem))
                .build();

        order2 = new Order.Builder()
                .setCustomer(customer)
                .setOrderDate(LocalDate.of(2025, 6, 6))
                .setOrderStatus(OrderStatus.CONFIRMED)
                .setTotalAmount(new Money.Builder().setAmount(85000).setCurrency("ZAR").build())
                .setOrderItems(Collections.singletonList(orderItem))
                .build();
    }

    @Test

    void createOrder() {
        // Test order1
        assertNotNull(order1);
        assertEquals(customer.getCustomerId(), order1.getCustomer().getCustomerId());
        assertEquals(LocalDate.of(2025, 5, 5), order1.getOrderDate());
        assertEquals(OrderStatus.PENDING, order1.getOrderStatus());
        assertEquals(45000, order1.getTotalAmount().getAmount());
        assertEquals("ZAR", order1.getTotalAmount().getCurrency());
        assertEquals(1, order1.getOrderItems().size());
        assertEquals(product.getProductId(), order1.getOrderItems().get(0).getProduct().getProductId());

        // Test order2
        assertNotNull(order2);
        assertEquals(customer.getCustomerId(), order2.getCustomer().getCustomerId());
        assertEquals(LocalDate.of(2025, 6, 6), order2.getOrderDate());
        assertEquals(OrderStatus.CONFIRMED, order2.getOrderStatus());
        assertEquals(85000, order2.getTotalAmount().getAmount());
        assertEquals("ZAR", order2.getTotalAmount().getCurrency());
        assertEquals(1, order2.getOrderItems().size());
        assertEquals(product.getProductId(), order2.getOrderItems().get(0).getProduct().getProductId());

        System.out.println("Order 1: " + order1);
        System.out.println("Order 2: " + order2);
    }

    @Test

    void updateOrder() {
        Order updatedOrder = new Order.Builder()
                .copy(order1)
                .setOrderStatus(OrderStatus.SHIPPED)
                .build();
        assertNotNull(updatedOrder);
        assertEquals(order1.getCustomer().getCustomerId(), updatedOrder.getCustomer().getCustomerId());
        assertEquals(order1.getOrderDate(), updatedOrder.getOrderDate());
        assertEquals(OrderStatus.SHIPPED, updatedOrder.getOrderStatus());
        assertEquals(order1.getTotalAmount().getAmount(), updatedOrder.getTotalAmount().getAmount());
        assertEquals(order1.getOrderItems().size(), updatedOrder.getOrderItems().size());
        System.out.println("Updated Order: " + updatedOrder);
    }

    @Test

    void createAndPersistOrder() {
        // Set Order reference in OrderItem before persisting
        orderItem = new OrderItem.Builder()
                .setOrder(order1)
                .setProduct(product)
                .setQuantity(3)
                .build();
        order1.getOrderItems().set(0, orderItem);

        Order persisted1 = orderService.create(order1);
        assertNotNull(persisted1);
        assertNotNull(persisted1.getId());
        assertEquals(order1.getCustomer().getCustomerId(), persisted1.getCustomer().getCustomerId());
        assertEquals(order1.getOrderDate(), persisted1.getOrderDate());
        assertEquals(order1.getOrderStatus(), persisted1.getOrderStatus());
        assertEquals(order1.getTotalAmount().getAmount(), persisted1.getTotalAmount().getAmount());
        assertEquals(1, persisted1.getOrderItems().size());
        assertEquals(product.getProductId(), persisted1.getOrderItems().get(0).getProduct().getProductId());

        // Set Order reference for order2
        orderItem = new OrderItem.Builder()
                .setOrder(order2)
                .setProduct(product)
                .setQuantity(3)
                .build();
        order2.getOrderItems().set(0, orderItem);

        Order persisted2 = orderService.create(order2);
        assertNotNull(persisted2);
        assertNotNull(persisted2.getId());
        assertEquals(order2.getCustomer().getCustomerId(), persisted2.getCustomer().getCustomerId());
        assertEquals(order2.getOrderDate(), persisted2.getOrderDate());
        assertEquals(order2.getOrderStatus(), persisted2.getOrderStatus());
        assertEquals(order2.getTotalAmount().getAmount(), persisted2.getTotalAmount().getAmount());
        assertEquals(1, persisted2.getOrderItems().size());
        assertEquals(product.getProductId(), persisted2.getOrderItems().get(0).getProduct().getProductId());

        System.out.println("Persisted Order 1: " + persisted1);
        System.out.println("Persisted Order 2: " + persisted2);
    }

    @Test
    void deleteOrder() {
        // Persist order1 with OrderItem
        orderItem = new OrderItem.Builder()
                .setOrder(order1)
                .setProduct(product)
                .setQuantity(3)
                .build();
        order1.getOrderItems().set(0, orderItem);

        Order persisted = orderService.create(order1);
        boolean deleted = orderService.delete(persisted.getId());
        assertTrue(deleted);

        Order deletedOrder = orderService.read(persisted.getId());
        assertNull(deletedOrder);
        System.out.println("Deleted Order: ID " + persisted.getId());
    }
}