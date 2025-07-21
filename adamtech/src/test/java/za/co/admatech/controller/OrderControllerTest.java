/*





OrderControllerTest.java



Author: Naqeebah Khan (219099073)



Date: 03 June 2025 */ package za.co.admatech.controller;

import org.junit.jupiter.api.*; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.context.SpringBootTest; import org.springframework.boot.test.web.server.LocalServerPort; import org.springframework.http.HttpMethod; import org.springframework.http.HttpStatus; import org.springframework.http.ResponseEntity; import org.springframework.boot.test.web.client.TestRestTemplate; import za.co.admatech.domain.Money; import za.co.admatech.domain.Order; import za.co.admatech.domain.Product; import za.co.admatech.domain.Customer; import za.co.admatech.domain.enums.OrderStatus; import za.co.admatech.domain.enums.ProductCategory; import za.co.admatech.domain.enums.ProductType;
import za.co.admatech.factory.*;
import za.co.admatech.repository.ProductRepository; import za.co.admatech.repository.CustomerRepository;

import jakarta.transaction.Transactional; import java.math.BigDecimal; import java.time.LocalDate; import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
class OrderControllerTest {
    @LocalServerPort private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private String baseUrl;

    private static Order order;
    private static Product product;
    private static Customer customer;

    @BeforeAll
    public static void setUp() {
        product = ProductFactory.createProduct(
                22L,
                "Product Name",
                "Hardware Test product",
                new Money(235.00, "USD"),
                ProductCategory.LAPTOPS,
                ProductType.DESKTOP
        );
        customer = CustomerFactory.createCustomer(
                23L,
                "John",
                "Doe",
                "john.doe@example.com",
                "1234567890",
                CartFactory.createCart(24L, null, List.of()),
                List.of(AddressFactory.createAddress(
                        25L,
                        (short) 12,
                        "Main Street",
                        "Suburb",
                        "City",
                        "Province",
                        (short) 1234
                )),
                List.of()
        );
        order = OrderFactory.createOrder(
                234L,
                LocalDate.of(2020, 1, 1),
                OrderStatus.PENDING,
                new Money(2335.00, "USD"),
                List.of(),
                customer
        );
    }

    @BeforeEach
    public void init() {
        baseUrl = "http://localhost:" + port + "/orders";
    }

    @Test
    void create() {
        productRepository.save(product);
        customerRepository.save(customer);
        ResponseEntity<Order> response = restTemplate.postForEntity(baseUrl, order, Order.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(order.getId(), response.getBody().getId());
        assertEquals(order.getOrderStatus(), response.getBody().getOrderStatus());
        System.out.println("Created Order: " + response.getBody());

        // Update order for subsequent tests
        order = response.getBody();
    }

    @Test
    void read() {
        String url = baseUrl + "/" + order.getId();
        ResponseEntity<Order> response = restTemplate.getForEntity(url, Order.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(order.getId(), response.getBody().getId());
        assertEquals(order.getOrderStatus(), response.getBody().getOrderStatus());
        System.out.println("Read Order: " + response.getBody());
    }

    @Test
    void update() {
        Order updatedOrder = new Order.Builder()
                .copy(order)
                .setOrderStatus(OrderStatus.CONFIRMED)
                .build();
        ResponseEntity<Order> response = restTemplate.exchange(baseUrl, HttpMethod.PUT, new org.springframework.http.HttpEntity<>(updatedOrder), Order.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(order.getId(), response.getBody().getId());
        assertEquals(OrderStatus.CONFIRMED, response.getBody().getOrderStatus());
        System.out.println("Updated Order: " + response.getBody());
    }

    @Test
    void delete() {
        String url = baseUrl + "/" + order.getId();
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        System.out.println("Deleted Order: " + order.getId());

        // Verify deletion
        ResponseEntity<Order> readResponse = restTemplate.getForEntity(url, Order.class);
        assertEquals(HttpStatus.NOT_FOUND, readResponse.getStatusCode());
    }

    @Test
    void getAll() {
        ResponseEntity<Order[]> response = restTemplate.getForEntity(baseUrl, Order[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length >= 0);
        System.out.println("All Orders: " + List.of(response.getBody()));
    }

}