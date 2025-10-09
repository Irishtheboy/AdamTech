package za.co.admatech.factory;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import za.co.admatech.domain.Money;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class MoneyFactoryTest {

    private static Money m = MoneyFactory.createMoney(100, "USD");

    @Test
    @Order(1)
    public void testCreateMoney() {
        assertNotNull(m);
        assertEquals(100, m.getAmount());
        assertEquals("USD", m.getCurrency());
        System.out.println(m.toString());
    }

    @Test
    @Order(2)
    public void testMoneyThatFails() {
        Money invalidMoney = MoneyFactory.createMoney(-100, null);
        assertNull(invalidMoney);
        System.out.println("Invalid money creation test passed");
    }

}