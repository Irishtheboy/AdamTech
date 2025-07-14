/*OrderItemServiceTest.java
  Order Item Service  class
  Author: Naqeebah Khan (219099073)
  Date: 24 May 2025
 */

package za.co.admatech.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.OrderItem;
import za.co.admatech.factory.OrderItemFactory;
import za.co.admatech.service.order_item_domain_service.OrderItemService;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class OrderItemServiceTest {

    @Autowired
    private OrderItemService service;
    private OrderItem orderItem = OrderItemFactory.createOrderItem();

    @Test
    void a_create() {
        OrderItem createdItem = service.create(orderItem);
        assertNotNull(createdItem);
        System.out.println(createdItem);
    }

    @Test
    void b_read() {
        OrderItem readItem = service.read(orderItem.getId());
        assertNotNull(readItem);
        System.out.println(readItem);
    }

    @Test
    void c_update() {
        OrderItem updatedItem = new OrderItem.Builder()
                .copy(orderItem)
                .setQuantity(2)
                .build();
        OrderItem updated = service.update(updatedItem);
        assertNotNull(updated);
        System.out.println(updated);
    }

    @Test
    void d_delete() {
        boolean deleted = service.delete(orderItem.getOrderItemId());
        assertTrue(deleted);
        System.out.println(deleted);
    }

    @Test
    void e_getOrderItems() {
        System.out.println(service.getAll());
    }
}