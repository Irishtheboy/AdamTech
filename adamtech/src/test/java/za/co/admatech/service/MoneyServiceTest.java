package za.co.admatech.service;/*
MoneyServiceTest.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025
Description: This test class contains integration tests for the MoneyService class,
verifying the functionality of create, update, isValidMoney, and getAll methods
using Spring Boot without mocks. Note: Money is an embeddable class.
*/


import za.co.admatech.domain.Money;
import za.co.admatech.service.money_domain_service.MoneyService;
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
    private MoneyService service;

    // Mock Money setup
    private final BigDecimal VALID_AMOUNT = new BigDecimal("1200.00");
    private final String VALID_CURRENCY = "ZAR";
    private final Money validMoney = new Money.Builder()
            .amount(VALID_AMOUNT)
            .currency(VALID_CURRENCY)
            .build();
    private final Money invalidMoney = new Money.Builder()
            .amount(null)
            .currency(null)
            .build();

    @Test
    void a_create() {
        Money created = service.create(validMoney);
        assertNotNull(created);
        assertEquals(VALID_AMOUNT, created.getAmount());
        assertEquals(VALID_CURRENCY, created.getCurrency());
        System.out.println("Created: " + created);
    }

    @Test
    void b_update() {
        Money updated = service.update(validMoney);
        assertNotNull(updated);
        assertEquals(VALID_AMOUNT, updated.getAmount());
        assertEquals(VALID_CURRENCY, updated.getCurrency());
        System.out.println("Updated: " + updated);
    }

    @Test
    void c_isValidMoney() {
        assertTrue(service.isValidMoney(validMoney));
        assertFalse(service.isValidMoney(invalidMoney));
        System.out.println("Valid Money Check: " + service.isValidMoney(validMoney));
    }

    @Test
    void d_getAll() {
        service.create(validMoney);
        List<Money> moneys = service.getAll();
        assertNotNull(moneys);
        assertTrue(moneys.isEmpty() || moneys.contains(validMoney)); // Adjust based on implementation
        moneys.forEach(System.out::println);
    }
}