/*OrderServiceTest.java
  Order Service Test class
  Author: Naqeebah Khan (219099073)
  Date: 24 May 2025
*/

package za.co.admatech.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Order;
import za.co.admatech.domain.enums.OrderStatus;
import za.co.admatech.factory.OrderFactory;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderServiceTest {

    @Autowired
    private IOrderService service;

    private Order order;

    @Test
    void a_create() {
        Order newOrder = OrderFactory.createOrder(
                "456",
                LocalDate.now(),
                OrderStatus.PENDING,
                new Money(500.00, "ZAR")
        );

        order = service.create(newOrder);
        assertNotNull(order);
        assertNotNull(order.getId(), "Order ID should not be null after save");
        System.out.println("Created order: " + order);
    }

    @Test
    void b_read() {
        assertNotNull(order, "Order must be created before reading");
        Order readOrder = service.read(order.getId());
        assertNotNull(readOrder);
        System.out.println("Read order: " + readOrder);
    }

    @Test
    void c_update() {
        assertNotNull(order, "Order must be created before updating");
        Order updateOrder = new Order.Builder().copy(order)
                .setOrderStatus(OrderStatus.COMPLETED)
                .build();

        Order updatedOrder = service.update(updateOrder);
        assertNotNull(updatedOrder);
        System.out.println("Updated order: " + updatedOrder);

        order = updatedOrder;  // update the order reference
    }

    @Test
    @Disabled
    void d_delete() {
        assertNotNull(order, "Order must be created before deleting");
        boolean deleted = service.delete(order.getId());
        assertTrue(deleted);
        System.out.println("Order successfully deleted");
    }

    @Test
    void e_getOrders() {
        System.out.println(service.getAll());
    }
}
