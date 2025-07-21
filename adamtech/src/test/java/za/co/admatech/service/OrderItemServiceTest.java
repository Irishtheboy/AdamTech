/*
OrderItemServiceTest.java
Author: Naqeebah Khan (219099073)
Date: 24 May 2025 */
package za.co.admatech.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.*;
import za.co.admatech.domain.enums.OrderStatus;
import za.co.admatech.domain.enums.ProductCategory;
import za.co.admatech.domain.enums.ProductType;
import za.co.admatech.factory.*;
import za.co.admatech.repository.CustomerRepository;
import za.co.admatech.repository.OrderRepository;
import za.co.admatech.repository.ProductRepository;
import za.co.admatech.service.order_item_domain_service.OrderItemService;

import jakarta.transaction.Transactional; import java.math.BigDecimal; import java.time.LocalDate; import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest @TestMethodOrder(MethodOrderer.OrderAnnotation.class) @Transactional class OrderItemServiceTest { @Autowired private OrderItemService service;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private static Product product;
    private static Order order;
    private static OrderItem orderItem;
    private static Customer customer;

    @BeforeAll
    public static void setUp() {
        product = ProductFactory.createProduct(
                987L,
                "Test Item",
                "Test Desc",
                new Money(2323, "ZAR"),
                ProductCategory.GAMING,
                ProductType.PERIPHERAL
        );
        customer = CustomerFactory.createCustomer(
                988L,
                "Jane",
                "Smith",
                "jane.smith@example.com",
                "0987654321",
                CartFactory.createCart(989L, null, List.of()),
                List.of(AddressFactory.createAddress(
                        990L,
                        (short) 12,
                        "Main Street",
                        "Suburb",
                        "City",
                        "Province",
                        (short) 1234
                )),
                List.of()
        );
        order = (Order) OrderFactory.createOrder(
                991L,
                LocalDate.of(2020, 1, 1),
                OrderStatus.COMPLETED,
                new Money(2323, "ZAR"),
                List.of(),
                customer
        );
        orderItem = OrderItemFactory.createOrderItem(
                992L,
                1,
                new Money(2323, "ZAR"),
                (za.co.admatech.domain.Order) order,
                product
        );
    }

    @Test
    @Order(1)
    void create() {
        productRepository.save(product);
        Customer save = customerRepository.save(customer);
        OrderItem created = service.create(orderItem);
        assertNotNull(created);
        assertEquals(orderItem.getId(), created.getId());
        assertEquals(orderItem.getQuantity(), created.getQuantity());
        System.out.println("Created OrderItem: " + created);

        // Update orderItem for subsequent tests
        orderItem = created;
    }

    @Test
    @Order(2)
    void read() {
        OrderItem read = service.read(orderItem.getId());
        assertNotNull(read);
        assertEquals(orderItem.getId(), read.getId());
        assertEquals(orderItem.getQuantity(), read.getQuantity());
        System.out.println("Read OrderItem: " + read);
    }

    @Test
    @Order(3)
    void update() {
        OrderItem updatedItem = new OrderItem.Builder()
                .copy(orderItem)
                .setQuantity(2)
                .build();
        OrderItem updated = service.update(updatedItem);
        assertNotNull(updated);
        assertEquals(orderItem.getId(), updated.getId());
        assertEquals(2, updated.getQuantity());
        System.out.println("Updated OrderItem: " + updated);
    }

    @Test
    @Order(4)
    void delete() {
        assertDoesNotThrow(() -> service.delete(orderItem.getId()));
        assertNull(service.read(orderItem.getId()));
        System.out.println("Deleted OrderItem: " + orderItem.getId());
    }

    @Test
    @Order(5)
    void getAll() {
        List<OrderItem> items = service.getAll();
        assertNotNull(items);
        assertTrue(items.size() >= 0);
        System.out.println("All OrderItems: " + items);
    }

}