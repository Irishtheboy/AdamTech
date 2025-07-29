package za.co.admatech.factory;


import za.co.admatech.domain.*;


import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Order;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderItemFactoryTest {

    private static Money unitPrice = new Money.Builder().amount(new BigDecimal("500")).currency("ZAR").build();
    private static Order order = new Order.Builder().id("1").build();
    private static Product product = new Product.Builder().productId("1").build();

    private static OrderItem oi1 = OrderItemFactory.createOrderItem("1", "prod001", 2, unitPrice, order, product);
    private static OrderItem oi2 = OrderItemFactory.createOrderItem("2", "prod002", 1, unitPrice, order, product);
    private static OrderItem oi3 = OrderItemFactory.createOrderItem("3", "prod003", 3, unitPrice, order, product);

    @Test
    @org.junit.jupiter.api.Order(1)
    public void testCreateOrderItem1() {
        assertNotNull(oi1);
        assertNotNull(oi1.getOrderItemId());
        System.out.println(oi1.toString());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    public void testCreateOrderItem2() {
        assertNotNull(oi2);
        assertNotNull(oi2.getOrderItemId());
        System.out.println(oi2.toString());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    public void testCreateOrderItem3() {
        assertNotNull(oi3);
        assertNotNull(oi3.getOrderItemId());
        System.out.println(oi3.toString());
    }
}