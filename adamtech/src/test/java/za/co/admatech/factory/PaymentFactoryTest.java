package za.co.admatech.factory;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Payment;
import za.co.admatech.domain.enums.PaymentStatus;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PaymentFactoryTest {

    static Money amount = new Money(100.00, "ZAR");
    private static Payment p = PaymentFactory.createPayment(LocalDate.now(), "ORDER123", amount, PaymentStatus.PENDING);

    @Test
    @Order(1)
    public void testCreatePayment() {
        assertNotNull(p);
        assertEquals(LocalDate.now(), p.getPaymentDate());

        assertEquals(amount, p.getAmount());
        assertEquals(PaymentStatus.PENDING, p.getPaymentStatus());
        System.out.println(p.toString());
    }

    @Test
    @Order(2)
    public void testPaymentThatFails() {
        Payment invalidPayment = PaymentFactory.createPayment(null, null, null, null);
        assertNull(invalidPayment);
        System.out.println("Invalid payment creation test passed");
    }
}