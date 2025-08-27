package za.co.admatech.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Order;
import za.co.admatech.domain.OrderItem;
import za.co.admatech.domain.Product;
import za.co.admatech.repository.OrderRepository;
import za.co.admatech.repository.ProductRepository;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
@Transactional
class OrderItemControllerTest {

    private OrderItem orderItem;
    private Product product;
    private Order order;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    private static final String BASE_URL = "/orderItem";

    @BeforeEach
    public void setUp() {
        Money unitPrice = new Money.Builder()
                .setAmount(29)
                .setCurrency("USD")
                .build();

        product = new Product.Builder()
                .setName("Test Product")
                .setDescription("Test Description")
                .setSku("TP1000")
                .setPrice(unitPrice)
                .setCategoryId("CAT1")
                .build();

        product = productRepository.save(product);
        System.out.println("Saved product: " + product);
        assertNotNull(product.getProductId(), "Persisted product should have an ID");

        order = new Order.Builder()
                .setOrderDate(LocalDate.parse("2025-08-27"))
                .setOrderStatus(za.co.admatech.domain.enums.OrderStatus.PENDING)
                .build();

        order = orderRepository.save(order);
        System.out.println("Saved order: " + order);
        assertNotNull(order.getId(), "Persisted order should have an ID");

        orderItem = new OrderItem.Builder()
                .setProduct(product)
                .setOrder(order)
                .setQuantity(2)
                .setUnitPrice(unitPrice)
                .build();
    }

    @Test
    void a_create() {
        String url = BASE_URL + "/create";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OrderItem> request = new HttpEntity<>(orderItem, headers);
        try {
            ResponseEntity<OrderItem> postResponse = restTemplate.postForEntity(url, request, OrderItem.class);
            assertNotNull(postResponse, "Response should not be null");
            assertEquals(200, postResponse.getStatusCodeValue(), "Should return 200 OK");
            OrderItem createdOrderItem = postResponse.getBody();
            assertNotNull(createdOrderItem, "Created order item should not be null");
            assertNotNull(createdOrderItem.getId(), "Order item ID should be generated");
            assertEquals(orderItem.getQuantity(), createdOrderItem.getQuantity(), "Quantity should match");
            assertEquals(orderItem.getUnitPrice().getAmount(), createdOrderItem.getUnitPrice().getAmount(), "Unit price should match");

            orderItem = new OrderItem.Builder().copy(createdOrderItem).build();
            System.out.println("Created: " + createdOrderItem);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            System.err.println("Error response: " + e.getResponseBodyAsString());
            throw e;
        }
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + orderItem.getId();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Void> request = new HttpEntity<>(headers);
        try {
            ResponseEntity<OrderItem> response = restTemplate.exchange(url, HttpMethod.GET, request, OrderItem.class);
            assertNotNull(response.getBody(), "Response body should not be null");
            assertEquals(orderItem.getId(), response.getBody().getId(), "Order item ID should match");
            assertEquals(orderItem.getQuantity(), response.getBody().getQuantity(), "Quantity should match");
            System.out.println("Read: " + response.getBody());
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            System.err.println("Error response: " + e.getResponseBodyAsString());
            throw e;
        }
    }

    @Test
    void c_update() {
        Money updatedUnitPrice = new Money.Builder()
                .setAmount(39)
                .setCurrency("USD")
                .build();

        OrderItem updatedOrderItem = new OrderItem.Builder()
                .copy(orderItem)
                .setQuantity(3)
                .setUnitPrice(updatedUnitPrice)
                .build();

        String url = BASE_URL + "/update";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OrderItem> request = new HttpEntity<>(updatedOrderItem, headers);
        try {
            ResponseEntity<OrderItem> response = restTemplate.exchange(url, HttpMethod.PUT, request, OrderItem.class);
            assertNotNull(response.getBody(), "Response body should not be null");
            assertEquals(orderItem.getId(), response.getBody().getId(), "Order item ID should match");
            assertEquals(3, response.getBody().getQuantity(), "Updated quantity should be 3");
            assertEquals(39, response.getBody().getUnitPrice().getAmount(), "Updated unit price should be 39");
            System.out.println("Updated: " + response.getBody());
            orderItem = response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            System.err.println("Error response: " + e.getResponseBodyAsString());
            throw e;
        }
    }

    @Test
    void d_delete() {
        String url = BASE_URL + "/delete/" + orderItem.getId();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Void> request = new HttpEntity<>(headers);
        try {
            ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.DELETE, request, Boolean.class);
            assertNotNull(response.getBody(), "Response body should not be null");
            assertTrue(response.getBody(), "Delete should return true");

            ResponseEntity<OrderItem> readResponse = restTemplate.getForEntity(BASE_URL + "/read/" + orderItem.getId(), OrderItem.class);
            assertEquals(404, readResponse.getStatusCodeValue(), "Order item should not exist after deletion");
            System.out.println("Deleted: true");
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            System.err.println("Error response: " + e.getResponseBodyAsString());
            throw e;
        }
    }

    @Test
    void e_getAll() {
        Money unitPrice = new Money.Builder()
                .setAmount(49)
                .setCurrency("USD")
                .build();

        OrderItem newOrderItem = new OrderItem.Builder()
                .setProduct(product)
                .setOrder(order)
                .setQuantity(1)
                .setUnitPrice(unitPrice)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OrderItem> createRequest = new HttpEntity<>(newOrderItem, headers);
        ResponseEntity<OrderItem> createResponse = restTemplate.postForEntity(BASE_URL + "/create", createRequest, OrderItem.class);
        assertEquals(200, createResponse.getStatusCodeValue(), "Create should return 200 OK");

        String url = BASE_URL + "/getAll";
        HttpEntity<Void> request = new HttpEntity<>(headers);
        try {
            ResponseEntity<OrderItem[]> response = restTemplate.exchange(url, HttpMethod.GET, request, OrderItem[].class);
            assertNotNull(response.getBody(), "Response body should not be null");
            assertTrue(response.getBody().length > 0, "Should return at least one order item");
            System.out.println("Get All:");
            for (OrderItem oi : response.getBody()) {
                System.out.println(oi);
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            System.err.println("Error response: " + e.getResponseBodyAsString());
            throw e;
        }
    }
}