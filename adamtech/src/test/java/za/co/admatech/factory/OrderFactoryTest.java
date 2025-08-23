package za.co.admatech.factory;

import za.co.admatech.domain.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderFactoryTest {

    private static Money totalAmount = new Money.Builder().amount(new BigDecimal("2000")).currency("ZAR").build();
    private static Customer customer = new Customer.Builder().customerId("1").build();
    private static List<OrderItem> items = new ArrayList<>();

    private static Order o1 = OrderFactory.createOrder("1", LocalDateTime.of(2025, 7, 29, 13, 35), totalAmount, customer, items);
    private static Order o2 = OrderFactory.createOrder("2", LocalDateTime.of(2025, 7, 29, 13, 35), totalAmount, customer, items);
    private static Order o3 = OrderFactory.createOrder("3", LocalDateTime.of(2025, 7, 29, 13, 35), totalAmount, customer, items);

    @Test
    @org.junit.jupiter.api.Order(1)
    public void testCreateOrder1() {
        assertNotNull(o1);
        assertNotNull(o1.getId());
        System.out.println(o1.toString());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    public void testCreateOrder2() {
        assertNotNull(o2);
        assertNotNull(o2.getId());
        System.out.println(o2.toString());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    public void testCreateOrder3() {
        assertNotNull(o3);
        assertNotNull(o3.getId());
        System.out.println(o3.toString());
    }
}