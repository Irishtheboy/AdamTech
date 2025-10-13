package za.co.admatech.factory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Money;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MoneyFactoryTest {

    @Test
    void buildMoney_ShouldReturnValidMoney_WhenValidParametersProvided() {
        // Given
        int amount = 150000;
        String currency = "ZAR";

        // When
        Money money = MoneyFactory.buildMoney(amount, currency);

        // Then
        assertNotNull(money);
        assertEquals(amount, money.getAmount());
        assertEquals(currency, money.getCurrency());
    }

    @Test
    void buildMoney_ShouldHandleZeroAmount() {
        // Given
        int amount = 0;
        String currency = "USD";

        // When
        Money money = MoneyFactory.buildMoney(amount, currency);

        // Then
        assertNotNull(money);
        assertEquals(0, money.getAmount());
        assertEquals(currency, money.getCurrency());
    }

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

    @Test
    void buildMoney_ShouldCreateUniqueInstances_WhenCalledMultipleTimes() {
        // Given
        int amount = 50000;
        String currency = "GBP";

        // When
        Money money1 = MoneyFactory.buildMoney(amount, currency);
        Money money2 = MoneyFactory.buildMoney(amount, currency);

        // Then
        assertNotNull(money1);
        assertNotNull(money2);
        assertNotSame(money1, money2); // Different instances
        assertEquals(money1.getAmount(), money2.getAmount()); // Same data
        assertEquals(money1.getCurrency(), money2.getCurrency());
    }

    @Test
    void buildMoney_ShouldHandleDifferentCurrencies() {
        // Given & When
        Money zarMoney = MoneyFactory.buildMoney(10000, "ZAR");
        Money usdMoney = MoneyFactory.buildMoney(1000, "USD");
        Money eurMoney = MoneyFactory.buildMoney(900, "EUR");

        // Then
        assertEquals("ZAR", zarMoney.getCurrency());
        assertEquals("USD", usdMoney.getCurrency());
        assertEquals("EUR", eurMoney.getCurrency());
        
        assertEquals(10000, zarMoney.getAmount());
        assertEquals(1000, usdMoney.getAmount());
        assertEquals(900, eurMoney.getAmount());
    }

    @Test
    void buildMoney_ShouldHandleMaxIntegerValue() {
        // Given
        int maxAmount = Integer.MAX_VALUE;
        String currency = "ZAR";

        // When
        Money money = MoneyFactory.buildMoney(maxAmount, currency);

        // Then
        assertNotNull(money);
        assertEquals(Integer.MAX_VALUE, money.getAmount());
        assertEquals(currency, money.getCurrency());
    }

    @Test
    void buildMoney_ShouldHandleMinIntegerValue() {
        // Given
        int minAmount = Integer.MIN_VALUE;
        String currency = "ZAR";

        // When
        Money money = MoneyFactory.buildMoney(minAmount, currency);

        // Then
        assertNotNull(money);
        assertEquals(Integer.MIN_VALUE, money.getAmount());
        assertEquals(currency, money.getCurrency());
    }
}
