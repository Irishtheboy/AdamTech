package za.co.admatech.service;/*
CartOrderServiceTest.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025
Description: This test class contains integration tests for the CartOrderService class,
verifying the functionality of create, read, update, delete, and getAll methods
using Spring Boot without mocks.
*/


import jakarta.persistence.EntityNotFoundException;
import za.co.admatech.domain.CartOrder;
import za.co.admatech.domain.Order;
import za.co.admatech.domain.enums.OrderStatus;
import za.co.admatech.factory.CartOrderFactory;
import za.co.admatech.service.cartorder_domain_service.ICartOrderService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class CartOrderServiceTest {

    @Autowired
    private ICartOrderService service;

    private static CartOrder cartOrder;
    private static Order order;

    @BeforeAll
    public static void setUp() {
        // Mock or create a simple Order (assuming Order exists)
        order = new Order(); // Placeholder; replace with actual OrderFactory if available
        order.setId("order-201");

        cartOrder = CartOrderFactory.createCartOrder(
                "order-101",
                OrderStatus.PENDING,
                order
        );
    }

    @Test
    void a_create() {
        CartOrder created = service.create(cartOrder);
        assertNotNull(created);
        assertEquals("order-101", created.getId());
        assertEquals(OrderStatus.PENDING, created.getOrderStatus());
        assertEquals("order-201", created.getOrder().getId());
        System.out.println("Created CartOrder: " + created);
    }

    @Test
    void b_read() {
        CartOrder saved = service.create(cartOrder);
        CartOrder read = service.read(saved.getId());
        assertNotNull(read);
        assertEquals("order-101", read.getId());
        assertEquals(OrderStatus.PENDING, read.getOrderStatus());
        assertEquals("order-201", read.getOrder().getId());
        System.out.println("Read CartOrder: " + read);
    }

    @Test
    void c_update() {
        CartOrder saved = service.create(cartOrder);
        CartOrder updated = saved.copy();
        updated.setOrderStatus(OrderStatus.CONFIRMED); // Example update
        CartOrder result = service.update(updated);
        assertNotNull(result);
        assertEquals("order-101", result.getId());
        assertEquals(OrderStatus.CONFIRMED, result.getOrderStatus());
        System.out.println("Updated CartOrder: " + result);
    }

    @Test
    void d_delete() {
        CartOrder saved = service.create(cartOrder);
        boolean deleted = service.delete(saved.getId());
        assertTrue(deleted);
        assertThrows(EntityNotFoundException.class, () -> service.read(saved.getId()));
        System.out.println("Deleted: " + deleted);
    }

    @Test
    void e_getAll() {
        service.create(cartOrder);
        List<CartOrder> orders = service.getAll();
        assertNotNull(orders);
        assertFalse(orders.isEmpty());
        orders.forEach(System.out::println);
    }
}