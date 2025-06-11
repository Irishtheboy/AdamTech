/*OrderServiceTest.java
  Order Service Test class
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
import za.co.admatech.domain.Order;
import za.co.admatech.domain.enums.OrderStatus;
import za.co.admatech.factory.OrderFactory;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class OrderServiceTest {
    @Autowired
    private IOrderService service;
    private Order order = OrderFactory.createOrder(
            "456",
            LocalDate.now(),
            OrderStatus.PENDING,
            new Money(500.00, "ZAR")
    );

    @Test
    void a_create() {
        Order createdOrder = service.create(order);
        assertNotNull(createdOrder);
        System.out.println(createdOrder);
    }

    @Test
    void b_read() {
        Order readOrder = service.read(order.getId());
        assertNotNull(readOrder);
        System.out.println(readOrder);
    }

    @Test
    void c_update() {
        Order updateOrder = new Order.Builder().copy(order)
                .setOrderStatus(OrderStatus.COMPLETED)
                .build();
        Order updatedOrder = service.update(updateOrder);
        assertNotNull(updatedOrder);
        System.out.println(updateOrder);
    }

    @Test
    void d_delete() {
        boolean deleted = service.delete(order.getId());
        assertTrue(deleted);
        System.out.println("Order successfully deleted");
    }

    @Test
    void e_getOrders() {
        System.out.println(service.getAll());
    }
}