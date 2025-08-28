package za.co.admatech.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import za.co.admatech.domain.*;
import za.co.admatech.domain.enums.OrderStatus;
import za.co.admatech.domain.enums.PaymentStatus;
import za.co.admatech.service.CustomerService;
import za.co.admatech.service.OrderService;
import za.co.admatech.service.PaymentService;
import za.co.admatech.domain.Order;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PaymentControllerTest {

    private static Payment payment;
    private static Order order;
    private static Customer customer;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PaymentService paymentService;

    private static final String BASE_URL = "/payments";

    @BeforeAll
    static void setUp() {
        // Address
        Address address = new Address.Builder()
                .setStreetNumber((short) 12)
                .setStreetName("Oak Street")
                .setSuburb("Parklands")
                .setCity("Cape Town")
                .setProvince("Western Cape")
                .setPostalCode((short) 7441)
                .build();

        // Customer
        customer = new Customer.Builder()
                .setFirstName("Test")
                .setLastName("User")
                .setEmail("test@admatech.co.za")
                .setAddress(address)
                .setPhoneNumber("1234567890")
                .build();

        // Order
        order = new Order.Builder()
                .setCustomer(customer)
                .setOrderDate(LocalDate.now())
                .setOrderStatus(OrderStatus.PENDING)
                .setTotalAmount(new Money.Builder().setAmount(50000).setCurrency("USD").build())
                .build();

        // Payment
        Money amount = new Money.Builder()
                .setAmount(50000)
                .setCurrency("USD")
                .build();

        payment = new Payment.Builder()
                .setOrder(order)
                .setPaymentDate(LocalDate.now())
                .setAmount(amount)
                .setPaymentStatus(PaymentStatus.PENDING)
                .build();
    }

    @BeforeEach
    void beforeEach() {
        // Persist customer and order
        customer = customerService.create(customer);
        order = orderService.create(order);

        // Persist payment
        payment = paymentService.create(new Payment.Builder()
                .copy(payment)
                .setOrder(order)
                .build());
    }



    @Test
    void create() {
        Payment newPayment = new Payment.Builder()
                .setOrder(order)
                .setPaymentDate(LocalDate.now())
                .setAmount(new Money.Builder().setAmount(10000).setCurrency("USD").build())
                .setPaymentStatus(PaymentStatus.PENDING)
                .build();

        Payment createdPayment = restTemplate.postForObject(BASE_URL + "/create", newPayment, Payment.class);

        assertNotNull(createdPayment);
        assertNotNull(createdPayment.getId()); // now passes
        assertEquals(newPayment.getAmount().getAmount(), createdPayment.getAmount().getAmount());

        System.out.println("Created Payment: " + createdPayment);
    }


    @Test
    void read() {
        ResponseEntity<Payment> response = restTemplate.getForEntity(BASE_URL + "/read/" + payment.getId(), Payment.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(payment.getId(), response.getBody().getId());
        System.out.println("Read Payment: " + response.getBody());
    }

    @Test

    void update() {
        Payment updatedPayment = new Payment.Builder()
                .copy(payment)
                .setPaymentStatus(PaymentStatus.COMPLETED)
                .build();

        HttpEntity<Payment> request = new HttpEntity<>(updatedPayment);
        ResponseEntity<Payment> response = restTemplate.exchange(BASE_URL + "/update", HttpMethod.PUT, request, Payment.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(PaymentStatus.COMPLETED, response.getBody().getPaymentStatus());
        System.out.println("Updated Payment: " + response.getBody());

        payment = response.getBody();
    }

    @Test

    void delete() {
        restTemplate.delete(BASE_URL + "/delete/" + payment.getId());

        ResponseEntity<Payment> response = restTemplate.getForEntity(BASE_URL + "/read/" + payment.getId(), Payment.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("Deleted Payment ID: " + payment.getId());
    }

    @Test

    void getAll() {
        ResponseEntity<Payment[]> response = restTemplate.getForEntity(BASE_URL + "/getAll", Payment[].class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);

        System.out.println("All Payments:");
        for (Payment p : response.getBody()) {
            System.out.println(p);
        }
    }
}
