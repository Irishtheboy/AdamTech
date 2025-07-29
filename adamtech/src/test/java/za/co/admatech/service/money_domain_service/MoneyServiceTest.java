/*
MoneyServiceTest.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025
Description: This test class contains integration tests for the MoneyService class,
verifying the functionality of create, update, isValidMoney, and getAll methods
using Spring Boot without mocks.
*/
package za.co.admatech.service.money_domain_service;

import za.co.admatech.domain.Money;
import za.co.admatech.service.money_domain_service.IMoneyService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class MoneyServiceTest {

    @Autowired
    private IMoneyService service;

    private static Money validMoney;
    private static Money invalidMoney;

    @BeforeAll
    public static void setUp() {
        validMoney = new Money.Builder()
                .amount(new BigDecimal("1200.00"))
                .currency("ZAR")
                .build();

        invalidMoney = new Money.Builder()
                .amount(null)
                .currency("")
                .build();
    }

    @Test
    void a_create() {
        Money created = service.create(validMoney);
        assertNotNull(created);
        assertEquals(new BigDecimal("1200.00"), created.getAmount());
        assertEquals("ZAR", created.getCurrency());
        System.out.println("Created Money: " + created);
    }

    @Test
    void b_update() {
        Money saved = service.create(validMoney);
        Money updated = saved.copy();
        updated.setAmount(new BigDecimal("1500.00")); // Example update
        Money result = service.update(updated);
        assertNotNull(result);
        assertEquals(new BigDecimal("1500.00"), result.getAmount());
        assertEquals("ZAR", result.getCurrency());
        System.out.println("Updated Money: " + result);
    }

    @Test
    void c_isValidMoney() {
        assertTrue(service.isValidMoney(validMoney));
        assertFalse(service.isValidMoney(invalidMoney));
        System.out.println("Valid Money check: " + service.isValidMoney(validMoney));
        System.out.println("Invalid Money check: " + service.isValidMoney(invalidMoney));
    }

    @Test
    void d_getAll() {
        service.create(validMoney);
        List<Money> moneys = service.getAll();
        assertNotNull(moneys);
        assertFalse(moneys.isEmpty()); // Assuming getAll returns created instances
        moneys.forEach(System.out::println);
    }
}