package za.co.admatech.factory;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import za.co.admatech.domain.Payment;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PaymentFactoryTest {

    private static Payment p = PaymentFactory.createPayment("", "", "200", "PENIDNG");

    @Test
    @Order(1)
    public void testCreatePayment() {
        assertNotNull(p);
        System.out.println(p.toString());
    }

    @Test
    @Order(2)
    public void testPaymentThatFails() {
        //fail();

        assertNotNull(p);
        System.out.println(p.toString());
    }
}