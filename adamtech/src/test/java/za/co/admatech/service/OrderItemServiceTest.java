package za.co.admatech.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Order;
import za.co.admatech.domain.OrderItem;
import za.co.admatech.domain.Product;
import za.co.admatech.domain.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderItemServiceTest {

    @Autowired
    private OrderItemService service;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    private static OrderItem savedOrderItem;
    private static Order order;
    private static Product product;

    @BeforeAll
    static void setup() {
        // Initialize Product
        product = new Product.Builder()
                .setName("Test Product")
                .setDescription("A test product")
                .setSku("TEST123")
                .setPrice(new Money.Builder().setAmount(10000).setCurrency("ZAR").build())
                .setCategoryId("CAT1")
                .build();

        // Initialize Order
        order = new Order.Builder()
                .setOrderDate(LocalDate.now())
                .setOrderStatus(OrderStatus.PENDING)
                .setTotalAmount(new Money.Builder().setAmount(10000).setCurrency("ZAR").build())
                .build();
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void a_create() {
        product = productService.create(product);
        order = orderService.create(order);

        OrderItem orderItem = new OrderItem.Builder()
                .setProduct(product)
                .setQuantity(1)
                .setUnitPrice(new Money.Builder().setAmount(10000).setCurrency("ZAR").build())
                .setOrder(order)
                .build();

        savedOrderItem = service.create(orderItem);

        assertNotNull(savedOrderItem);
        assertNotNull(savedOrderItem.getId());
        assertEquals(1, savedOrderItem.getQuantity());
        assertEquals(product.getProductId(), savedOrderItem.getProduct().getProductId());
        assertEquals(order.getId(), savedOrderItem.getOrder().getId());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void b_read() {
        assertNotNull(savedOrderItem);

        OrderItem readItem = service.read(savedOrderItem.getId());
        assertNotNull(readItem);
        assertEquals(savedOrderItem.getId(), readItem.getId());
        assertEquals(savedOrderItem.getQuantity(), readItem.getQuantity());
        assertEquals(savedOrderItem.getUnitPrice().getAmount(), readItem.getUnitPrice().getAmount());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void c_update() {
        // Recreate dependencies
        product = productService.create(product);
        order = orderService.create(order);

        // Create the OrderItem for this test
        OrderItem orderItem = new OrderItem.Builder()
                .setProduct(product)
                .setQuantity(1)
                .setUnitPrice(new Money.Builder().setAmount(10000).setCurrency("ZAR").build())
                .setOrder(order)
                .build();
        OrderItem saved = service.create(orderItem);

        // Now update
        OrderItem updatedItem = new OrderItem.Builder()
                .copy(saved)
                .setQuantity(2)
                .build();

        OrderItem updated = service.update(updatedItem);

        assertNotNull(updated);
        assertEquals(2, updated.getQuantity());
    }


    @Test
    @org.junit.jupiter.api.Order(4)
    void e_getAll() {
        // Add one more OrderItem
        OrderItem newOrderItem = new OrderItem.Builder()
                .setProduct(product)
                .setQuantity(3)
                .setUnitPrice(new Money.Builder().setAmount(10000).setCurrency("ZAR").build())
                .setOrder(order)
                .build();
        service.create(newOrderItem);

        List<OrderItem> all = service.getAll();
        assertNotNull(all);
        assertTrue(all.size() >= 2);

        // Print safely without triggering lazy loading
        all.forEach(item -> System.out.println(
                "OrderItem ID: " + item.getId() +
                        ", Product Name: " + item.getProduct().getName() +
                        ", Quantity: " + item.getQuantity() +
                        ", UnitPrice: " + item.getUnitPrice().getAmount()
        ));
    }
}
