package za.co.admatech.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Order;
import za.co.admatech.domain.Payment;
import za.co.admatech.domain.enums.PaymentStatus;
import za.co.admatech.factory.PaymentFactory;
import za.co.admatech.service.payment_domain_service.PaymentService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class PaymentServiceTest {

    @Autowired
    private PaymentService service;

    // Mock Order and Payment setup
    private final Order order = new Order.Builder()
            .setId(01L)
            .setOrderDate(LocalDate.of(2025, 5, 24))
            .setOrderStatus(za.co.admatech.domain.enums.OrderStatus.CONFIRMED)
            .setTotalAmount(new Money(1200, "ZAR"))
            .build();

    private final Payment payment = PaymentFactory.createPayment(
            101L,
            order,
            LocalDate.of(2025, 5, 25),
            new Money(1200, "ZAR"),
            PaymentStatus.PENDING
    );

    @Test
    void a_create() {
        Payment created = service.create(payment);
        assertNotNull(created);
        assertEquals(PaymentStatus.PENDING, created.getPaymentStatus());
        System.out.println("Created: " + created);
    }

    @Test
    void b_read() {
        Payment saved = service.create(payment);
        Payment read = service.read(saved.getId());
        assertNotNull(read);
        System.out.println("Read: " + read);
    }

    @Test
    void c_update() {
        Payment saved = service.create(payment);
        Payment updated = new Payment.Builder()
                .copy(saved)
                .setPaymentStatus(PaymentStatus.COMPLETED)
                .build();
        Payment result = service.update(updated);
        assertNotNull(result);
        assertEquals(PaymentStatus.COMPLETED, result.getPaymentStatus());
        System.out.println("Updated: " + result);
    }

    @Test
    void d_delete() {
        Payment saved = service.create(payment);
        boolean deleted = service.delete(saved.getId());
        assertTrue(deleted);
        assertNull(service.read(saved.getId()));
        System.out.println("Deleted: " + deleted);
    }

    @Test
    void e_getPayments() {
        List<Payment> payments = service.getAll();
        assertNotNull(payments);
        assertFalse(payments.isEmpty());
        payments.forEach(System.out::println);
    }
}
