/*OrderItemFactory Test Class
  Naqeebah Khan 219099073
  17 May 2025*/
package za.co.admatech.factory;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.OrderItem;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderItemFactoryTest {

    private static OrderItem orderItem1 = OrderItemFactory.createOrderItem(
            "PROD101",
            2,
            new Money.Builder()
                    .setAmount(199)
                    .build()
    );

    private static OrderItem orderItem2 = OrderItemFactory.createOrderItem(
            "PROD102",
            5,
            new Money.Builder()
                    .setAmount(99)
                    .build()
    );

    private static OrderItem updatedOrderItem;

    @Test
    void createOrderItem1() {
        assertNotNull(orderItem1);
        System.out.println(orderItem1);

    }

    @Test
    void createOrderItem2() {
        assertNotNull(orderItem2);
        System.out.println(orderItem2);

    }

    @Test
    void read() {
        OrderItem read = orderItem1;
        assertNotNull(read);
        System.out.println(read);

    }

    @Test
    void update() {
        updatedOrderItem = new OrderItem.Builder()
                .copy(orderItem1)
                .setQuantity(10)
                .build();
        assertNotNull(updatedOrderItem);
        assertEquals(10, updatedOrderItem.getQuantity());
        System.out.println(updatedOrderItem);

    }

    @Test
    void delete() {
        orderItem1 = null;
        assertNull(orderItem1);
        System.out.println("Order item deleted successfully");

    }
}