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
import za.co.admatech.domain.OrderItem;
import za.co.admatech.domain.Product;
import za.co.admatech.domain.enums.OrderStatus;
import za.co.admatech.domain.enums.ProductType;
import za.co.admatech.factory.OrderFactory;
import za.co.admatech.factory.OrderItemFactory;
import za.co.admatech.service.order_domain_service.IOrderService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class OrderServiceTest {
    @Autowired
    private IOrderService service;
    private Product product = new Product.Builder()
            .setProductName("Test Item")
            .setProductDescription("Test Desc")
            .setProductPriceAmount(new Money(100, "ZAR"))
            .setProductCategory("GAMING")
            .setProductType(ProductType.PERIPHERAL)
            .build();

    LocalDate date = LocalDate.of(2020, 1, 1);
    private Order order = new Order.Builder()
            .setId(231l)
            .setOrderDate(date)
            .setOrderStatus(OrderStatus.COMPLETED)
            .setTotalAmount(new Money(100, "ZAR"))
            .build();

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