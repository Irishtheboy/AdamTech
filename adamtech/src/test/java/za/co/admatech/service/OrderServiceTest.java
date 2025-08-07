package za.co.admatech.service;/*
OrderServiceTest.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025
Description: This test class contains integration tests for the OrderService class,
verifying the functionality of create, read, update, delete, and getAll methods
using Spring Boot without mocks.
*/


import jakarta.persistence.EntityNotFoundException;
import za.co.admatech.domain.Order;
import za.co.admatech.domain.Money;
import za.co.admatech.service.order_domain_service.OrderService;
import za.co.admatech.util.Helper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class OrderServiceTest {

    @Autowired
    private OrderService service;

    // Mock Order setup
    private final String VALID_ID = "1";
    private final LocalDateTime VALID_DATE = LocalDateTime.of(2025, 5, 24, 0, 0);
    private final Money VALID_MONEY = new Money.Builder()
            .amount(new java.math.BigDecimal("1200.00"))
            .currency("ZAR")
            .build();
    private final Order order = new Order.Builder()
            .id(VALID_ID)
            .orderDate(VALID_DATE)
            .totalAmount(VALID_MONEY)
            .build();

    @Test
    void a_create() {
        Order created = service.create(order);
        assertNotNull(created);
        assertEquals(VALID_ID, created.getId());
        System.out.println("Created: " + created);
    }

    @Test
    void b_read() {
        Order saved = service.create(order);
        Order read = service.read(saved.getId());
        assertNotNull(read);
        assertEquals(VALID_ID, read.getId());
        System.out.println("Read: " + read);
    }

    @Test
    void c_update() {
        Order saved = service.create(order);
        Order updated = saved.copy();
        updated.setOrderDate(LocalDateTime.of(2025, 5, 25, 0, 0)); // Example update logic
        Order result = service.update(updated);
        assertNotNull(result);
        assertEquals(VALID_ID, result.getId());
        assertEquals(LocalDateTime.of(2025, 5, 25, 0, 0), result.getOrderDate());
        System.out.println("Updated: " + result);
    }

    @Test
    void d_delete() {
        Order saved = service.create(order);
        boolean deleted = service.delete(saved.getId());
        assertTrue(deleted);
        assertThrows(EntityNotFoundException.class, () -> service.read(saved.getId()));
        System.out.println("Deleted: " + deleted);
    }

    @Test
    void e_getOrders() {
        service.create(order);
        List<Order> orders = service.getAll();
        assertNotNull(orders);
        assertFalse(orders.isEmpty());
        orders.forEach(System.out::println);
    }
}