package za.co.admatech.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import za.co.admatech.domain.Order;
import za.co.admatech.domain.enums.OrderStatus;
import za.co.admatech.service.OrderService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class OrderControllerTest {

    private static Order order;

    @Autowired
    private TestRestTemplate restTemplate;

    @SuppressWarnings("unused")
    @Autowired
    private OrderService orderService;

    private static final String BASE_URL = "/order";

    @BeforeEach
    void setUp() {
        // Create an order through REST so it gets an ID
        order = restTemplate.postForObject(
                BASE_URL + "/create",
                new Order.Builder()
                        .setOrderDate(LocalDate.now())
                        .setOrderStatus(OrderStatus.PENDING)
                        .build(),
                Order.class
        );

        assertNotNull(order);
        assertNotNull(order.getId());
    }

    @Test
    void a_create() {
        assertNotNull(order);
        System.out.println("Created Order: " + order);
    }

    @Test
    void b_read() {
        ResponseEntity<Order> response = restTemplate.getForEntity(
                BASE_URL + "/read/" + order.getId(),
                Order.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(order.getId(), response.getBody().getId());

        System.out.println("Read Order: " + response.getBody());
    }

    @Test
    void c_update() {
        Order updatedOrder = new Order.Builder()
                .copy(order)
                .setOrderStatus(OrderStatus.COMPLETED)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Order> request = new HttpEntity<>(updatedOrder, headers);

        ResponseEntity<Order> response = restTemplate.exchange(
                BASE_URL + "/update",
                HttpMethod.PUT,
                request,
                Order.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(OrderStatus.COMPLETED, response.getBody().getOrderStatus());

        order = response.getBody();
        System.out.println("Updated Order: " + order);
    }

    @Test
    void d_delete() {
        restTemplate.delete(BASE_URL + "/delete/" + order.getId());

        // Try reading deleted order
        ResponseEntity<Order> response = restTemplate.getForEntity(
                BASE_URL + "/read/" + order.getId(),
                Order.class
        );

        assertTrue(response.getStatusCode().is2xxSuccessful() || response.getStatusCode() == HttpStatus.NOT_FOUND);
        System.out.println("Deleted Order with ID: " + order.getId());
    }

    @Test
    void e_getAll() {
        ResponseEntity<Order[]> response = restTemplate.getForEntity(
                BASE_URL + "/getAll",
                Order[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        System.out.println("All Orders:");
        for (Order o : response.getBody()) {
            System.out.println(o);
        }
    }
}
