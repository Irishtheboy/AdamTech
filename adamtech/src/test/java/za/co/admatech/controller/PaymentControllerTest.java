package za.co.admatech.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import za.co.admatech.domain.Address;
import za.co.admatech.domain.Customer;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Order;
import za.co.admatech.domain.Payment;
import za.co.admatech.domain.enums.OrderStatus;
import za.co.admatech.domain.enums.PaymentStatus;
import za.co.admatech.service.CustomerService;
import za.co.admatech.service.OrderService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional // Roll back database changes after each test
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

    private static final String BASE_URL = "/payments";

    @BeforeAll
    static void setUp() {
        // Initialize Address
        Address address = new Address.Builder()
                .setStreetNumber((short) 12)
                .setStreetName("Oak Street")
                .setSuburb("Parklands")
                .setCity("Cape Town")
                .setProvince("Western Cape")
                .setPostalCode((short) 7441)
                .build();

        // Initialize Customer
        customer = new Customer.Builder()
                .setFirstName("Test")
                .setLastName("User")
                .setEmail("test@admatech.co.za")
                .setAddress(address)
                .setPhoneNumber("1234567890")
                .build();

        // Initialize Order
        order = new Order.Builder()
                .setCustomer(customer)
                .setOrderDate(LocalDate.now())
                .setOrderStatus(OrderStatus.PENDING)
                .setTotalAmount(new Money.Builder().setAmount(50000).setCurrency("USD").build()) // 500.00 USD
                .build();

        // Initialize Payment
        Money amount = new Money.Builder()
                .setAmount(50000) // 500.00 USD
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
        // Persist dependencies before each test
        customer = customerService.create(customer);
        order = orderService.create(order);
        payment = new Payment.Builder()
                .copy(payment)
                .setOrder(order)
                .build();
    }

    @Test

    void create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Payment> response = restTemplate.postForEntity(url, payment, Payment.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals(payment.getAmount().getAmount(), response.getBody().getAmount().getAmount());
        assertEquals(payment.getPaymentStatus(), response.getBody().getPaymentStatus());
        assertEquals(order.getId(), response.getBody().getOrder().getId());

        // Save the generated ID for later
        payment = new Payment.Builder().copy(response.getBody()).build();
        System.out.println("Created: " + payment);
    }

    @Test

    void read() {
        String url = BASE_URL + "/read/" + payment.getId();
        ResponseEntity<Payment> response = restTemplate.getForEntity(url, Payment.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(payment.getId(), response.getBody().getId());
        assertEquals(payment.getAmount().getAmount(), response.getBody().getAmount().getAmount());
        assertEquals(payment.getPaymentStatus(), response.getBody().getPaymentStatus());
        System.out.println("Read: " + response.getBody());
    }

    @Test

    void update() {
        Payment updatedPayment = new Payment.Builder()
                .copy(payment)
                .setPaymentStatus(PaymentStatus.COMPLETED)
                .build();

        String url = BASE_URL + "/update/" + payment.getId();
        HttpEntity<Payment> request = new HttpEntity<>(updatedPayment);
        ResponseEntity<Payment> response = restTemplate.exchange(url, HttpMethod.PUT, request, Payment.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(payment.getId(), response.getBody().getId());
        assertEquals(PaymentStatus.COMPLETED, response.getBody().getPaymentStatus());
        assertEquals(payment.getAmount().getAmount(), response.getBody().getAmount().getAmount());

        payment = response.getBody();
        System.out.println("Updated: " + payment);
    }

    @Test

    void delete() {
        String url = BASE_URL + "/delete/" + payment.getId();
        restTemplate.delete(url);

        ResponseEntity<Payment> response = restTemplate.getForEntity(BASE_URL + "/read/" + payment.getId(), Payment.class);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
        System.out.println("Deleted: ID " + payment.getId());
    }

    @Test

    void getAll() {
        // Create a new Payment for getAll test
        Payment newPayment = new Payment.Builder()
                .setOrder(order)
                .setPaymentDate(LocalDate.now())
                .setAmount(new Money.Builder().setAmount(10000).setCurrency("USD").build()) // 100.00 USD
                .setPaymentStatus(PaymentStatus.PENDING)
                .build();
        restTemplate.postForEntity(BASE_URL + "/create", newPayment, Payment.class);

        String url = BASE_URL + "/getAll";
        ResponseEntity<Payment[]> response = restTemplate.getForEntity(url, Payment[].class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        System.out.println("All Payments:");
        for (Payment p : response.getBody()) {
            System.out.println(p);
        }
    }
}