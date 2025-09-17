/*
 * OrderItemFactoryTest.java
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
import za.co.admatech.service.OrderItemService;
import za.co.admatech.service.OrderService;
import za.co.admatech.service.ProductService;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class OrderItemFactoryTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    private Customer customer;
    private Product product1;
    private Product product2;
    private Order order;
    private OrderItem orderItem1;
    private OrderItem orderItem2;

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

        // Initialize Products
        product1 = new Product.Builder()
                .setName("Product 1")
                .setDescription("Test product 1")
                .setSku("PROD101")
                .setPrice(new Money.Builder().setAmount(19900).setCurrency("ZAR").build()) // 199.00 ZAR
                .setCategoryId("CAT1")
                .build();

        product2 = new Product.Builder()
                .setName("Product 2")
                .setDescription("Test product 2")
                .setSku("PROD102")
                .setPrice(new Money.Builder().setAmount(9900).setCurrency("ZAR").build()) // 99.00 ZAR
                .setCategoryId("CAT2")
                .build();

        // Initialize Order
        order = new Order.Builder()
                .setCustomer(customer)
                .setOrderDate(LocalDate.of(2025, 5, 5))
                .setOrderStatus(OrderStatus.PENDING)
                .setTotalAmount(new Money.Builder().setAmount(49600).setCurrency("ZAR").build()) // 199*2 + 99*5 = 496.00 ZAR
                .setOrderItems(Collections.emptyList())
                .build();

        // Initialize OrderItems
        orderItem1 = new OrderItem.Builder()
                .setOrder(order)
                .setProduct(product1)
                .setQuantity(2)
                .setUnitPrice(new Money.Builder().setAmount(19900).setCurrency("ZAR").build())
                .build();

        orderItem2 = new OrderItem.Builder()
                .setOrder(order)
                .setProduct(product2)
                .setQuantity(5)
                .setUnitPrice(new Money.Builder().setAmount(9900).setCurrency("ZAR").build())
                .build();
    }

    @BeforeEach
    void beforeEach() {
        // Persist dependencies
        customer = customerService.create(customer);
        product1 = productService.create(product1);
        product2 = productService.create(product2);
        order = orderService.create(order);

        // Update OrderItems with persisted Order
        orderItem1 = new OrderItem.Builder()
                .setOrder(order)
                .setProduct(product1)
                .setQuantity(2)
                .setUnitPrice(new Money.Builder().setAmount(19900).setCurrency("ZAR").build())
                .build();

        orderItem2 = new OrderItem.Builder()
                .setOrder(order)
                .setProduct(product2)
                .setQuantity(5)
                .setUnitPrice(new Money.Builder().setAmount(9900).setCurrency("ZAR").build())
                .build();
    }

    @Test

    void createOrderItem() {
        // Test orderItem1
        assertNotNull(orderItem1);
        assertEquals(order.getId(), orderItem1.getOrder().getId());
        assertEquals(product1.getProductId(), orderItem1.getProduct().getProductId());
        assertEquals(2, orderItem1.getQuantity());
        assertEquals(19900, orderItem1.getUnitPrice().getAmount());
        assertEquals("ZAR", orderItem1.getUnitPrice().getCurrency());

        // Test orderItem2
        assertNotNull(orderItem2);
        assertEquals(order.getId(), orderItem2.getOrder().getId());
        assertEquals(product2.getProductId(), orderItem2.getProduct().getProductId());
        assertEquals(5, orderItem2.getQuantity());
        assertEquals(9900, orderItem2.getUnitPrice().getAmount());
        assertEquals("ZAR", orderItem2.getUnitPrice().getCurrency());

        System.out.println("OrderItem 1: " + orderItem1);
        System.out.println("OrderItem 2: " + orderItem2);
    }

    @Test

    void updateOrderItem() {
        OrderItem updatedOrderItem = new OrderItem.Builder()
                .copy(orderItem1)
                .setQuantity(10)
                .build();
        assertNotNull(updatedOrderItem);
        assertEquals(orderItem1.getOrder().getId(), updatedOrderItem.getOrder().getId());
        assertEquals(orderItem1.getProduct().getProductId(), updatedOrderItem.getProduct().getProductId());
        assertEquals(10, updatedOrderItem.getQuantity());
        assertEquals(orderItem1.getUnitPrice().getAmount(), updatedOrderItem.getUnitPrice().getAmount());
        assertEquals(orderItem1.getUnitPrice().getCurrency(), updatedOrderItem.getUnitPrice().getCurrency());

        System.out.println("Updated OrderItem: " + updatedOrderItem);
    }

    @Test

    void createAndPersistOrderItem() {
        // Update Order with OrderItems
        order.getOrderItems().add(orderItem1);
        order.getOrderItems().add(orderItem2);
        order = orderService.update(order);

        OrderItem persisted1 = orderItemService.create(orderItem1);
        assertNotNull(persisted1);
        assertNotNull(persisted1.getId());
        assertEquals(orderItem1.getOrder().getId(), persisted1.getOrder().getId());
        assertEquals(orderItem1.getProduct().getProductId(), persisted1.getProduct().getProductId());
        assertEquals(orderItem1.getQuantity(), persisted1.getQuantity());
        assertEquals(orderItem1.getUnitPrice().getAmount(), persisted1.getUnitPrice().getAmount());
        assertEquals(orderItem1.getUnitPrice().getCurrency(), persisted1.getUnitPrice().getCurrency());

        OrderItem persisted2 = orderItemService.create(orderItem2);
        assertNotNull(persisted2);
        assertNotNull(persisted2.getId());
        assertEquals(orderItem2.getOrder().getId(), persisted2.getOrder().getId());
        assertEquals(orderItem2.getProduct().getProductId(), persisted2.getProduct().getProductId());
        assertEquals(orderItem2.getQuantity(), persisted2.getQuantity());
        assertEquals(orderItem2.getUnitPrice().getAmount(), persisted2.getUnitPrice().getAmount());
        assertEquals(orderItem2.getUnitPrice().getCurrency(), persisted2.getUnitPrice().getCurrency());

        System.out.println("Persisted OrderItem 1: " + persisted1);
        System.out.println("Persisted OrderItem 2: " + persisted2);
    }

    @Test

    void deleteOrderItem() {
        // Persist OrderItem
        order.getOrderItems().add(orderItem1);
        order = orderService.update(order);
        OrderItem persisted = orderItemService.create(orderItem1);

        boolean deleted = orderItemService.delete(persisted.getId());
        assertTrue(deleted);

        OrderItem deletedOrderItem = orderItemService.read(persisted.getId());
        assertNull(deletedOrderItem);

        System.out.println("Deleted OrderItem: ID " + persisted.getId());
    }
}