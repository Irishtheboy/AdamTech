package za.co.admatech.factory;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Order;
import za.co.admatech.domain.Payment;
import za.co.admatech.domain.enums.OrderStatus;
import za.co.admatech.domain.enums.PaymentStatus;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PaymentFactoryTest {

    static Order order = new Order.Builder()
            .setId(123L)
            .setOrderDate(LocalDate.of(2025, 5, 24))
            .setOrderStatus(OrderStatus.CONFIRMED)
            .setTotalAmount(new Money(100, "ZAR"))
            .setOrderItems(List.of()) // empty list for testing
            .build();

    static Money amount = new Money(100.00, "ZAR");

    static Payment validPayment = PaymentFactory.createPayment(
            101L,
            order,
            LocalDate.now(),
            amount,
            PaymentStatus.PENDING
    );

    static Payment invalidPayment = PaymentFactory.createPayment(
            102L,
            null, // ❌ No order passed
            null, // ❌ Invalid date
            null, // ❌ Null amount
            null  // ❌ Null status
    );

    @Test
    void testCreateValidPayment() {
        assertNotNull(validPayment);
        assertEquals(PaymentStatus.PENDING, validPayment.getPaymentStatus());
        System.out.println("Valid Payment: " + validPayment);
    }

    @Test
    void testCreateInvalidPayment() {
        assertNull(invalidPayment);
        System.out.println("Invalid Payment creation returned null as expected.");
    }
}
