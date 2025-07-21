package za.co.admatech.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Order;
import za.co.admatech.domain.OrderItem;
import za.co.admatech.domain.Product;
import za.co.admatech.domain.enums.ProductType;
import za.co.admatech.factory.OrderFactory;
import za.co.admatech.factory.OrderItemFactory;
import za.co.admatech.factory.ProductFactory;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.class)
class OrderItemControllerTest {

    private static final String BASE_URL = "http://localhost:8080/orderItems";
    private RestTemplate template = new RestTemplate();

    private static Product product;
    private static Order order;
    private static OrderItem orderItem;

    @BeforeAll
    public static void setUp() {
        product = ProductFactory.createProduct(
                1001L,
                "Test Widget",
                "Sample Product Description",
                new Money(299.99, "ZAR"),
                "GADGETS",
                ProductType.PERIPHERAL
        );

        order = OrderFactory.createOrder(
                2001L,
                LocalDate.of(2025, 6, 6),
                za.co.admatech.domain.enums.OrderStatus.CONFIRMED,
                new Money(599.99, "ZAR"),
                List.of(), // No order items inside order yet
                null       // No customer reference for simplicity
        );

        orderItem = OrderItemFactory.createOrderItem(
                3001L,
                2,
                new Money(299.99, "ZAR"),
                order,
                product
        );
    }

    @Test
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<OrderItem> response = template.postForEntity(url, orderItem, OrderItem.class);
        assertNotNull(response.getBody());
        assertEquals(orderItem.getId(), response.getBody().getId());
        System.out.println("Created OrderItem: " + response.getBody());
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + orderItem.getId();
        ResponseEntity<OrderItem> response = template.getForEntity(url, OrderItem.class);
        assertNotNull(response.getBody());
        assertEquals(orderItem.getId(), response.getBody().getId());
        System.out.println("Read OrderItem: " + response.getBody());
    }

    @Test
    void c_update() {
        String url = BASE_URL + "/update/" + orderItem.getId();
        OrderItem updatedItem = new OrderItem.Builder()
                .copy(orderItem)
                .setQuantity(5)
                .build();
        ResponseEntity<OrderItem> response = template.postForEntity(url, updatedItem, OrderItem.class);
        assertNotNull(response.getBody());
        assertEquals(5, response.getBody().getQuantity());
        System.out.println("Updated OrderItem: " + response.getBody());
    }

    @Test
    void d_delete() {
        String url = BASE_URL + "/delete/" + orderItem.getId();
        ResponseEntity<Boolean> response = template.postForEntity(url, null, Boolean.class);
        assertTrue(response.getBody());
        System.out.println("Deleted OrderItem: " + orderItem.getId());
    }

    @Test
    void e_getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<OrderItem[]> response = template.getForEntity(url, OrderItem[].class);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length >= 0);
        System.out.println("All OrderItems: " + List.of(response.getBody()));
    }
}
