package za.co.admatech.service;/*
PaymentServiceTest.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025
Description: This test class contains integration tests for the PaymentService class,
verifying the functionality of create, read, update, delete, and getAll methods
using Spring Boot without mocks.
*/


import jakarta.persistence.EntityNotFoundException;
import za.co.admatech.domain.Order;
import za.co.admatech.domain.Payment;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.enums.PaymentStatus;
import za.co.admatech.factory.PaymentFactory;
import za.co.admatech.service.payment_domain_service.PaymentService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class PaymentServiceTest {

    @Autowired
    private PaymentService service;

    // Mock Order and Payment setup
    private final String ORDER_ID = "1";
    private final String PAYMENT_ID = "101";
    private final LocalDate PAYMENT_DATE = LocalDate.of(2025, 5, 25);
    private final Money VALID_MONEY = new Money.Builder()
            .amount(new java.math.BigDecimal("1200.00"))
            .currency("ZAR")
            .build();
    private final Order order = new Order.Builder()
            .id(ORDER_ID)
            .orderDate(LocalDateTime.of(2025, 5, 24, 0, 0))
            .totalAmount(VALID_MONEY)
            .build();
    private final Payment payment = PaymentFactory.createPayment(
            PAYMENT_ID,
            order.getId(),
            PAYMENT_DATE.atStartOfDay(),
            VALID_MONEY,
            PaymentStatus.PENDING
    );

    @Test
    void a_create() {
        Payment created = service.create(payment);
        assertNotNull(created);
        assertEquals(PAYMENT_ID, created.getPaymentId());
        assertEquals(PaymentStatus.PENDING, created.getPaymentStatus());
        System.out.println("Created: " + created);
    }

    @Test
    void b_read() {
        Payment saved = service.create(payment);
        Payment read = service.read(saved.getPaymentId());
        assertNotNull(read);
        assertEquals(PAYMENT_ID, read.getPaymentId());
        System.out.println("Read: " + read);
    }

    @Test
    void c_update() {
        Payment saved = service.create(payment);
        Payment updated = saved.copy();
        updated.setPaymentStatus(PaymentStatus.COMPLETED); // Example update logic
        Payment result = service.update(updated);
        assertNotNull(result);
        assertEquals(PAYMENT_ID, result.getPaymentId());
        assertEquals(PaymentStatus.COMPLETED, result.getPaymentStatus());
        System.out.println("Updated: " + result);
    }

    @Test
    void d_delete() {
        Payment saved = service.create(payment);
        boolean deleted = service.delete(saved.getPaymentId());
        assertTrue(deleted);
        assertThrows(EntityNotFoundException.class, () -> service.read(saved.getPaymentId()));
        System.out.println("Deleted: " + deleted);
    }

    @Test
    void e_getPayments() {
        service.create(payment);
        List<Payment> payments = service.getAll();
        assertNotNull(payments);
        assertFalse(payments.isEmpty());
        payments.forEach(System.out::println);
    }
}