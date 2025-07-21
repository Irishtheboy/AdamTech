/*OrderControllerTest.java
  Order controller Test Class
  Author: Naqeebah Khan (219099073)
  Date: 03 June 2025
 */
package za.co.admatech.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Order;
import za.co.admatech.domain.Product;
import za.co.admatech.domain.enums.OrderStatus;
import za.co.admatech.domain.enums.ProductType;
import za.co.admatech.factory.OrderFactory;
import za.co.admatech.factory.ProductFactory;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.class)
class OrderControllerTest {
    private static Order order;
    private static Product product;
    @BeforeAll
    public static void setUp(){
            product = ProductFactory.createProduct(
                 22l,
                 "Product Name",
                 "Hardware Test product",
                 new Money(250, "USD"),
                 "Hardware",
                 ProductType.DESKTOP
         );
        LocalDate date = LocalDate.of(2020, 1, 1);
        Order order = OrderFactory.createOrder(
                234L,
                date,
                OrderStatus.PENDING,
                new Money(234, "USD"),
                List.of(),
                null
        );
    }

    private RestTemplate template;
    private static final String BASE_URL = "http://localhost:8080/orders";

    @Test
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Order> response = this.template.postForEntity(url, order, Order.class);
        System.out.println(response.getBody());
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getId(), order.getId());
        System.out.println("Created Order: " + response.getBody());
    }

    @Test
    void read() {
        String url = BASE_URL + "/read/" + order.getId();
        ResponseEntity<Order> response = this.template.getForEntity(url, Order.class);
        System.out.println(response.getBody());
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getId(), order.getId());
        System.out.println("Read Order: " + response.getBody());
    }

    @Test
    void update() {
        String url = BASE_URL + "/update/" + order.getId();
        ResponseEntity<Order> response = this.template.postForEntity(url, order, Order.class);
        System.out.println(response.getBody());
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getId(), order.getId());
        System.out.println("Update Order: " + response.getBody());
    }

    @Test
    void delete() {
        String url = BASE_URL + "/delete/" + order.getId();
        ResponseEntity<Order> response = this.template.postForEntity(url, order, Order.class);
        System.out.println(response.getBody());
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getId(), order.getId());
        System.out.println("Delete Order: " + response.getBody());
    }

    @Test
    void getAll() {
        String url = BASE_URL + "/getAll";
    }
}