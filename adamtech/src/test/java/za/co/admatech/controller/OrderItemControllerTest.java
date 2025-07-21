/*





OrderItemControllerTest.java



Author: Naqeebah Khan (219099073)



Date: 03 June 2025 */ package za.co.admatech.controller;

import org.junit.jupiter.api.*; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.context.SpringBootTest; import org.springframework.boot.test.web.server.LocalServerPort; import org.springframework.http.HttpMethod; import org.springframework.http.HttpStatus; import org.springframework.http.ResponseEntity; import org.springframework.boot.test.web.client.TestRestTemplate; import za.co.admatech.domain.Money; import za.co.admatech.domain.Order; import za.co.admatech.domain.OrderItem; import za.co.admatech.domain.Product; import za.co.admatech.domain.Customer; import za.co.admatech.domain.enums.ProductCategory; import za.co.admatech.domain.enums.ProductType; import za.co.admatech.factory.AddressFactory; import za.co.admatech.factory.CartFactory; import za.co.admatech.factory.CustomerFactory; import za.co.admatech.factory.OrderFactory; import za.co.admatech.factory.OrderItemFactory; import za.co.admatech.factory.ProductFactory; import za.co.admatech.repository.ProductRepository; import za.co.admatech.repository.OrderRepository; import za.co.admatech.repository.CustomerRepository;

import jakarta.transaction.Transactional; import java.math.BigDecimal; import java.time.LocalDate; import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) @TestMethodOrder(MethodOrderer.OrderAnnotation.class) @Transactional class OrderItemControllerTest { @LocalServerPort private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private String baseUrl;

    private static Product product;
    private static Order order;
    private static OrderItem orderItem;
    private static Customer customer;

    @BeforeAll
    public static void setUp() {
        product = ProductFactory.createProduct(
                1001L,
                "Test Widget",
                "Sample Product Description",
                new Money(2335.00, "USD"),
                ProductCategory.GAMING,
                ProductType.PERIPHERAL
        );
        customer = CustomerFactory.createCustomer(
                1002L,
                "Jane",
                "Smith",
                "jane.smith@example.com",
                "0987654321",
                CartFactory.createCart(1003L, null, List.of()),
                List.of(AddressFactory.createAddress(
                        1004L,
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
                2001L,
                LocalDate.of(2025, 6, 6),
                za.co.admatech.domain.enums.OrderStatus.CONFIRMED,
                new Money(2335.00, "USD"),
                List.of(),
                customer
        );
        orderItem = OrderItemFactory.createOrderItem(
                3001L,
                2,
                new Money(2335.00, "USD"),
                order,
                product
        );
    }

    @BeforeEach
    public void init() {
        baseUrl = "http://localhost:" + port + "/order-items";
    }

    @Test
    void create() {
        productRepository.save(product);
        customerRepository.save(customer);
        orderRepository.save(order);
        ResponseEntity<OrderItem> response = restTemplate.postForEntity(baseUrl, orderItem, OrderItem.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(orderItem.getId(), response.getBody().getId());
        assertEquals(orderItem.getQuantity(), response.getBody().getQuantity());
        System.out.println("Created OrderItem: " + response.getBody());

        // Update orderItem for subsequent tests
        orderItem = response.getBody();
    }

    @Test
    void read() {
        String url = baseUrl + "/" + orderItem.getId();
        ResponseEntity<OrderItem> response = restTemplate.getForEntity(url, OrderItem.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(orderItem.getId(), response.getBody().getId());
        assertEquals(orderItem.getQuantity(), response.getBody().getQuantity());
        System.out.println("Read OrderItem: " + response.getBody());
    }

    @Test
    void update() {
        OrderItem updatedItem = new OrderItem.Builder()
                .copy(orderItem)
                .setQuantity(5)
                .build();
        ResponseEntity<OrderItem> response = restTemplate.exchange(baseUrl, HttpMethod.PUT, new org.springframework.http.HttpEntity<>(updatedItem), OrderItem.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(orderItem.getId(), response.getBody().getId());
        assertEquals(5, response.getBody().getQuantity());
        System.out.println("Updated OrderItem: " + response.getBody());
    }

    @Test
    void delete() {
        String url = baseUrl + "/" + orderItem.getId();
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        System.out.println("Deleted OrderItem: " + orderItem.getId());

        // Verify deletion
        ResponseEntity<OrderItem> readResponse = restTemplate.getForEntity(url, OrderItem.class);
        assertEquals(HttpStatus.NOT_FOUND, readResponse.getStatusCode());
    }

    @Test
    void getAll() {
        ResponseEntity<OrderItem[]> response = restTemplate.getForEntity(baseUrl, OrderItem[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length >= 0);
        System.out.println("All OrderItems: " + List.of(response.getBody()));
    }

}