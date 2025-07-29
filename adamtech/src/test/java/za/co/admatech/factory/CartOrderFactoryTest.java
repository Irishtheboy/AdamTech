package za.co.admatech.factory;

import za.co.admatech.domain.*;
import za.co.admatech.domain.Order;
import za.co.admatech.domain.enums.OrderStatus;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartOrderFactoryTest {

    private static Order order = new Order.Builder().id("1").build();

    private static CartOrder co1 = CartOrderFactory.createCartOrder("1", OrderStatus.CONFIRMED, order);
    private static CartOrder co2 = CartOrderFactory.createCartOrder("2", OrderStatus.PENDING, order);
    private static CartOrder co3 = CartOrderFactory.createCartOrder("3", OrderStatus.SHIPPED, order);

    @Test
    @org.junit.jupiter.api.Order(1)
    public void testCreateCartOrder1() {
        assertNotNull(co1);
        assertNotNull(co1.getId());
        System.out.println(co1.toString());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    public void testCreateCartOrder2() {
        assertNotNull(co2);
        assertNotNull(co2.getId());
        System.out.println(co2.toString());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    public void testCreateCartOrder3() {
        assertNotNull(co3);
        assertNotNull(co3.getId());
        System.out.println(co3.toString());
    }
}