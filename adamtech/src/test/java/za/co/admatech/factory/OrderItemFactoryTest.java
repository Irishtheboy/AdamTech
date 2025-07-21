/*OrderItemFactory Test Class
  Naqeebah Khan 219099073
  17 May 2025*/
package za.co.admatech.factory;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.OrderItem;
import za.co.admatech.domain.Product;
import za.co.admatech.domain.enums.ProductType;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderItemFactoryTest {

    private Product product = new Product.Builder()
            .setProductName("Test Item")
            .setProductDescription("Test Desc")
            .setProductPriceAmount(new Money(100, "ZAR"))
            .setProductCategory("GAMING")
            .setProductType(ProductType.PERIPHERAL)
            .build();

    private OrderItem orderItem = OrderItemFactory.createOrderItem(
            987L,
            1,
            new Money(100.00, "ZAR"),
            null,
            product
    );


    private static OrderItem updatedOrderItem;

    @Test
    void createOrderItem1() {
        assertNotNull(orderItem);
        System.out.println(orderItem);

    }

    @Test
    void read() {
        OrderItem read = orderItem;
        assertNotNull(read);
        System.out.println(read);

    }

    @Test
    void update() {
        updatedOrderItem = new OrderItem.Builder()
                .copy(orderItem)
                .setQuantity(10)
                .build();
        assertNotNull(updatedOrderItem);
        assertEquals(10, updatedOrderItem.getQuantity());
        System.out.println(updatedOrderItem);

    }

    @Test
    void delete() {
        orderItem = null;
        assertNull(orderItem);
        System.out.println("Order item deleted successfully");

    }
}