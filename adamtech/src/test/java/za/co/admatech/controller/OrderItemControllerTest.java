/* OrderItemControllerTest.java
   Order Item Controller Test Class
   Author: Naqeebah Khan (219099073)
   Date: 03 June 2025
 */
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
import za.co.admatech.domain.Order;
import za.co.admatech.domain.OrderItem;
import za.co.admatech.domain.Product;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class OrderItemControllerTest {

    private static OrderItem orderItem;
    private static Product product;
    private static Order order;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "/orderItem";

    @BeforeAll
    public static void setUp() {
        // Initialize dependencies
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

        order = new Order.Builder() // Assuming Order has a Builder
                .setOrderDate(LocalDate.parse("2025-08-27"))
                .setOrderStatus(za.co.admatech.domain.enums.OrderStatus.PENDING)
                .build();

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
        ResponseEntity<OrderItem> postResponse = this.restTemplate.postForEntity(url, orderItem, OrderItem.class);
        assertNotNull(postResponse, "Response should not be null");
        assertEquals(200, postResponse.getStatusCodeValue(), "Should return 200 OK");
        OrderItem createdOrderItem = postResponse.getBody();
        assertNotNull(createdOrderItem, "Created order item should not be null");
        assertNotNull(createdOrderItem.getId(), "Order item ID should be generated");
        assertEquals(orderItem.getQuantity(), createdOrderItem.getQuantity(), "Quantity should match");
        assertEquals(orderItem.getUnitPrice().getAmount(), createdOrderItem.getUnitPrice().getAmount(), "Unit price should match");

        // Save generated ID for later tests
        orderItem = new OrderItem.Builder().copy(createdOrderItem).build();

        System.out.println("Created: " + createdOrderItem);
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + orderItem.getId();
        ResponseEntity<OrderItem> response = this.restTemplate.getForEntity(url, OrderItem.class);
        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals(orderItem.getId(), response.getBody().getId(), "Order item ID should match");
        assertEquals(orderItem.getQuantity(), response.getBody().getQuantity(), "Quantity should match");
        System.out.println("Read: " + response.getBody());
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
        HttpEntity<OrderItem> request = new HttpEntity<>(updatedOrderItem);
        ResponseEntity<OrderItem> response = restTemplate.exchange(url, HttpMethod.PUT, request, OrderItem.class);

        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals(orderItem.getId(), response.getBody().getId(), "Order item ID should match");
        assertEquals(3, response.getBody().getQuantity(), "Updated quantity should be 3");
        assertEquals(39.99, response.getBody().getUnitPrice().getAmount(), "Updated unit price should be 39.99");
        System.out.println("Updated: " + response.getBody());

        // Update local reference for later tests
        orderItem = response.getBody();
    }

    @Test
    void d_delete() {
        String url = BASE_URL + "/delete/" + orderItem.getId();
        ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Boolean.class);
        assertNotNull(response.getBody(), "Response body should not be null");
        assertTrue(response.getBody(), "Delete should return true");

        // Verify deletion by attempting to read
        ResponseEntity<OrderItem> readResponse = this.restTemplate.getForEntity(BASE_URL + "/read/" + orderItem.getId(), OrderItem.class);
        assertNull(readResponse.getBody(), "Order item should not exist after deletion");
        System.out.println("Deleted: true");
    }

    @Test
    void e_getAll() {
        // Create a new order item to ensure there's at least one
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

        restTemplate.postForEntity(BASE_URL + "/create", newOrderItem, OrderItem.class);

        String url = BASE_URL + "/getAll";
        ResponseEntity<OrderItem[]> response = this.restTemplate.getForEntity(url, OrderItem[].class);
        assertNotNull(response.getBody(), "Response body should not be null");
        assertTrue(response.getBody().length > 0, "Should return at least one order item");
        System.out.println("Get All:");
        for (OrderItem oi : response.getBody()) {
            System.out.println(oi);
        }
    }
}