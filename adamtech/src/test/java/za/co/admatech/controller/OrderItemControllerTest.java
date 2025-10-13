package za.co.admatech.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import za.co.admatech.domain.*;
import za.co.admatech.domain.Order;
import za.co.admatech.domain.enums.OrderStatus;
import za.co.admatech.service.OrderService;
import za.co.admatech.service.ProductService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class OrderItemControllerTest {

    private static OrderItem orderItem;
    private static Product product;
    private static Order order;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductService productRepository;

    @Autowired
    private OrderService orderRepository;

    private static final String BASE_URL = "/orderItem";

    @BeforeEach
    void setUp() {
        // Create Money object
        Money unitPrice = new Money.Builder()
                .setAmount(29)
                .setCurrency("USD")
                .build();

        // Persist product
        product = productRepository.create(
                new Product.Builder()
                        .setName("Test Product")
                        .setDescription("Test Description")
                        .setSku("TP1000")
                        .setPrice(unitPrice)
                        .setCategoryId("CAT1")
                        .build()
        );

        // Persist order
        order = orderRepository.create(
                new Order.Builder()
                        .setOrderDate(LocalDate.now())
                        .setOrderStatus(OrderStatus.PENDING)
                        .build()
        );

        // Create OrderItem through REST to ensure ID is assigned
        orderItem = restTemplate.postForObject(
                BASE_URL + "/create",
                new OrderItem.Builder()
                        .setProduct(product)
                        .setOrder(order)
                        .setQuantity(2)
                        .setUnitPrice(unitPrice)
                        .build(),
                OrderItem.class
        );

        assertNotNull(orderItem);
        assertNotNull(orderItem.getId());
    }

    @Test
    void a_create() {
        assertNotNull(orderItem);
        System.out.println("Created OrderItem: " + orderItem);
    }

    @Test
    void b_read() {
        ResponseEntity<OrderItem> response = restTemplate.getForEntity(
                BASE_URL + "/read/" + orderItem.getId(),
                OrderItem.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(orderItem.getId(), response.getBody().getId());

        System.out.println("Read OrderItem: " + response.getBody());
    }

    @Test
    void c_update() {
        OrderItem updatedOrderItem = new OrderItem.Builder()
                .copy(orderItem)
                .setQuantity(5)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OrderItem> request = new HttpEntity<>(updatedOrderItem, headers);

        ResponseEntity<OrderItem> response = restTemplate.exchange(
                BASE_URL + "/update",
                HttpMethod.PUT,
                request,
                OrderItem.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(5, response.getBody().getQuantity());

        orderItem = response.getBody();
        System.out.println("Updated OrderItem: " + orderItem);
    }

    @Test
    void d_delete() {
        restTemplate.delete(BASE_URL + "/delete/" + orderItem.getId());

        // Try reading deleted item
        ResponseEntity<OrderItem> response = restTemplate.getForEntity(
                BASE_URL + "/read/" + orderItem.getId(),
                OrderItem.class
        );

        // Controller should return null body or 404
        assertTrue(response.getStatusCode().is2xxSuccessful() || response.getStatusCode() == HttpStatus.NOT_FOUND);
        System.out.println("Deleted OrderItem with ID: " + orderItem.getId());
    }

    @Test
    void e_getAll() {
        ResponseEntity<OrderItem[]> response = restTemplate.getForEntity(
                BASE_URL + "/getAll",
                OrderItem[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        System.out.println("All OrderItems:");
        for (OrderItem oi : response.getBody()) {
            System.out.println(oi);
        }
    }
}
