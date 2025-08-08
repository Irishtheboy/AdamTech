package za.co.admatech.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Payment;
import za.co.admatech.domain.enums.PaymentStatus;
import za.co.admatech.factory.PaymentFactory;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class) // Run tests alphabetically by method name
class PaymentServiceTest {

    @Autowired
    private IPaymentService service;

    private static Payment payment = PaymentFactory.createPayment(
            LocalDate.now(),
            "order123",
            new Money(1000.00, "ZAR"),
            PaymentStatus.PENDING
    );

    private static Payment payment2 = PaymentFactory.createPayment(
            LocalDate.now(),
            "order123",
            new Money(1000.00, "ZAR"),
            PaymentStatus.PENDING
    );

    @Test
    void a_create() {
        Payment created = service.create(payment);
        assertNotNull(created);
        assertNotNull(created.getId()); // Id should be generated
        payment = created; // Update static payment with id for other tests
        System.out.println("Created Payment: " + created);
    }

    @Test
    void b_read() {
        Payment read = service.read(payment.getId());
        assertNotNull(read);
        assertEquals(payment.getId(), read.getId());
        System.out.println("Read Payment: " + read);
    }

    @Test
    void c_update() {
        Payment updatedPayment = new Payment.Builder()
                .copy(payment)
                .setPaymentStatus(PaymentStatus.COMPLETED)
                .build();

        Payment updated = service.update(updatedPayment);
        assertNotNull(updated);
        assertEquals(PaymentStatus.COMPLETED, updated.getPaymentStatus());
        System.out.println("Updated Payment: " + updated);
    }

    @Test
    void d_delete() {
        boolean deleted = service.delete(payment2.getId());
        assertTrue(deleted);
        System.out.println("Deleted Payment with ID: " + payment2.getId());
    }

    @Test
    void e_getPayments() {
        List<Payment> payments = service.getPayments();
        assertNotNull(payments);
        System.out.println("All Payments: " + payments);
    }
}
