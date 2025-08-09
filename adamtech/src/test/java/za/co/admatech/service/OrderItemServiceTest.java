/*
  OrderItemServiceTest.java
  Author: Naqeebah Khan (219099073)
  Date: 24 May 2025
*/

package za.co.admatech.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.OrderItem;
import za.co.admatech.factory.OrderItemFactory;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderItemServiceTest {

    @Autowired
    private OrderItemService service;

    // Store the created OrderItem to reuse in read, update, and delete
    private static OrderItem savedOrderItem;

    @Test
    @Order(1)
    void a_create() {
        OrderItem orderItem = OrderItemFactory.createOrderItem(
                "987",
                1,
                new Money(100.00, "ZAR")
        );
        savedOrderItem = service.create(orderItem);
        assertNotNull(savedOrderItem);
        assertNotNull(savedOrderItem.getOrderItemId()); // Make sure ID is not null
        System.out.println("Created: " + savedOrderItem);
    }

    @Test
    @Order(2)
    void b_read() {
        assertNotNull(savedOrderItem);
        OrderItem readItem = service.read(savedOrderItem.getOrderItemId());
        assertNotNull(readItem);
        System.out.println("Read: " + readItem);
    }

    @Test
    @Order(3)
    void c_update() {
        assertNotNull(savedOrderItem);
        OrderItem updatedItem = new OrderItem.Builder()
                .copy(savedOrderItem)
                .setQuantity(2)
                .build();
        OrderItem updated = service.update(updatedItem);
        assertNotNull(updated);
        System.out.println("Updated: " + updated);
    }

    @Test
    @Order(4)
    @Disabled
    void d_delete() {
        assertNotNull(savedOrderItem);
        boolean deleted = service.delete(savedOrderItem.getOrderItemId());
        assertTrue(deleted);
        System.out.println("Deleted: " + deleted);
    }

    @Test
    @Order(5)
    void e_getAll() {
        System.out.println("All OrderItems: " + service.getAll());
    }
}
