package za.co.admatech.factory;

import org.junit.jupiter.api.Order;
import za.co.admatech.domain.*;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MoneyFactoryTest {

    private static Money m1 = MoneyFactory.createMoney(new BigDecimal("1000"), "ZAR");
    private static Money m2 = MoneyFactory.createMoney(new BigDecimal("500"), "USD");
    private static Money m3 = MoneyFactory.createMoney(new BigDecimal("0"), "EUR");

    @Test
    @Order(1)
    public void testCreateMoney1() {
        assertNotNull(m1);
        assertNotNull(m1.getAmount());
        System.out.println(m1.toString());
    }

    @Test
    @Order(2)
    public void testCreateMoney2() {
        assertNotNull(m2);
        assertNotNull(m2.getAmount());
        System.out.println(m2.toString());
    }

    @Test
    @Order(3)
    public void testCreateMoney3() {
        assertNotNull(m3);
        assertNotNull(m3.getAmount());
        System.out.println(m3.toString());
    }
}