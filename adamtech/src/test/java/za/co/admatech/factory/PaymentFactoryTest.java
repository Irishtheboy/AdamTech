package za.co.admatech.factory;

import za.co.admatech.domain.Money;
import za.co.admatech.domain.Payment;


import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.enums.PaymentStatus;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PaymentFactoryTest {

    private static Money paymentAmount1 = new Money.Builder().amount(new java.math.BigDecimal("1000")).currency("ZAR").build();
    private static Money paymentAmount2 = new Money.Builder().amount(new java.math.BigDecimal("500")).currency("ZAR").build();
    private static Money paymentAmount3 = new Money.Builder().amount(new java.math.BigDecimal("0")).currency("ZAR").build();

    private static Payment p1 = PaymentFactory.createPayment("1", "order001", LocalDateTime.of(2025, 7, 29, 13, 35), paymentAmount1, PaymentStatus.COMPLETED);
    private static Payment p2 = PaymentFactory.createPayment("2", "order002", LocalDateTime.of(2025, 7, 29, 13, 35), paymentAmount2, PaymentStatus.PENDING);
    private static Payment p3 = PaymentFactory.createPayment("3", "order003", LocalDateTime.of(2025, 7, 29, 13, 35), paymentAmount3, PaymentStatus.FAILED);

    @Test
    @Order(1)
    public void testCreatePayment1() {
        assertNotNull(p1);
        assertNotNull(p1.getPaymentId());
        System.out.println(p1.toString());
    }

    @Test
    @Order(2)
    public void testCreatePayment2() {
        assertNotNull(p2);
        assertNotNull(p2.getPaymentId());
        System.out.println(p2.toString());
    }

    @Test
    @Order(3)
    public void testCreatePayment3() {
        assertNotNull(p3);
        assertNotNull(p3.getPaymentId());
        System.out.println(p3.toString());
    }
}