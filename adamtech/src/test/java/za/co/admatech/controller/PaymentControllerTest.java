package za.co.admatech.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Payment;
import za.co.admatech.domain.enums.PaymentStatus;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class PaymentControllerTest {

    private static Payment payment;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "/payments";

    @BeforeAll
    public static void setUp() {
        Money amount = new Money.Builder()
                .setAmount(500)
                .setCurrency("USD")
                .build();

        payment = new Payment.Builder()
                .setOrderId("ORD123")
                .setPaymentDate(LocalDate.now())
                .setAmount(amount)
                .setPaymentStatus(PaymentStatus.PENDING)
                .build();
    }

    @Test
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Payment> response = restTemplate.postForEntity(url, payment, Payment.class);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());

        // Save the generated ID for later
        payment = new Payment.Builder().copy(response.getBody()).build();

        System.out.println("Created: " + payment);
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + payment.getId();
        ResponseEntity<Payment> response = restTemplate.getForEntity(url, Payment.class);
        assertNotNull(response.getBody());
        assertEquals(payment.getId(), response.getBody().getId());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    void c_update() {
        Payment updatedPayment = new Payment.Builder()
                .copy(payment)
                .setPaymentStatus(PaymentStatus.COMPLETED)
                .build();

        String url = BASE_URL + "/update/" + payment.getId();
        HttpEntity<Payment> request = new HttpEntity<>(updatedPayment);
        ResponseEntity<Payment> response = restTemplate.exchange(url, HttpMethod.PUT, request, Payment.class);

        assertNotNull(response.getBody());
        assertEquals(PaymentStatus.COMPLETED, response.getBody().getPaymentStatus());

        payment = response.getBody();
        System.out.println("Updated: " + payment);
    }

    @Test
    void d_delete() {
        String url = BASE_URL + "/delete/" + payment.getId();
        restTemplate.delete(url);

        ResponseEntity<Payment> response = restTemplate.getForEntity(BASE_URL + "/read/" + payment.getId(), Payment.class);
        assertNull(response.getBody());
        System.out.println("Deleted: true");
    }

    @Test
    void e_getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<Payment[]> response = restTemplate.getForEntity(url, Payment[].class);
        assertNotNull(response.getBody());
        System.out.println("All Payments:");
        for (Payment p : response.getBody()) {
            System.out.println(p);
        }
    }
}
