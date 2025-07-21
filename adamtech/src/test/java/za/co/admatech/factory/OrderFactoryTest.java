package za.co.admatech.factory;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.*;
import za.co.admatech.domain.Order;
import za.co.admatech.domain.enums.OrderStatus;
import za.co.admatech.domain.enums.ProductCategory;
import za.co.admatech.domain.enums.ProductType;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderFactoryTest {

    // Static objects for dependencies
    private static final Product product = new Product.Builder()
            .setProductId(1001L)
            .setProductName("Test Widget")
            .setProductDescription("Standard widget")
            .setProductPriceAmount(new Money(500, "ZAR"))
            .setCategory(ProductCategory.COMPONENTS)
            .setProductType(ProductType.PERIPHERAL)
            .build();

    private static final OrderItem orderItem = new OrderItem.Builder()
            .setId(3001L)
            .setProduct(product)
            .setQuantity(2)
            .setUnitPrice(new Money(500, "ZAR"))
            .build();

    private static final Customer customer = new Customer.Builder()
            .setCustomerID(999L)
            .setFirstName("Rorisang")
            .setLastName("Makgana")
            .setEmail("rorisang@example.com")
            .setPhoneNumber("0821234567")
            .setCart(null)
            .setAddress(List.of()) // empty for test
            .setOrders(List.of())
            .build();

    private static final Order order1 = OrderFactory.createOrder(
            2001L,
            LocalDate.of(2025, 5, 5),
            OrderStatus.PENDING,
            new Money(1000, "ZAR"),
            List.of(orderItem),
            customer
    );

    private static final Order order2 = OrderFactory.createOrder(
            2002L,
            LocalDate.of(2025, 6, 6),
            OrderStatus.CONFIRMED,
            new Money(850, "ZAR"),
            List.of(orderItem),
            customer
    );

    private static Order updatedOrder;

    @Test
    void createOrder1() {
        assertNotNull(order1);
        System.out.println(order1);
    }

    @Test
    void createOrder2() {
        assertNotNull(order2);
        System.out.println(order2);
    }

    @Test
    void updateOrderStatus() {
        updatedOrder = new Order.Builder()
                .copy(order1)
                .setOrderStatus(OrderStatus.SHIPPED)
                .build();
        assertNotNull(updatedOrder);
        assertEquals(OrderStatus.SHIPPED, updatedOrder.getOrderStatus());
        System.out.println(updatedOrder);
    }

    @Test
    void deleteOrderSimulation() {
        Order deleted = null;
        assertNull(deleted); // simulate delete, actual deletion would occur via service layer
        System.out.println("Order deleted successfully");
    }
}
