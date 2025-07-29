/*
OrderItemServiceTest.java
Author: Naqeebah Khan (219099073)
Date: 24 May 2025
Description: This test class contains integration tests for the OrderItemService class,
verifying the functionality of create, read, update, delete, and getAll methods
using Spring Boot without mocks.
*/

package za.co.admatech.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.*;
import za.co.admatech.domain.Order;
import za.co.admatech.domain.enums.OrderStatus;
import za.co.admatech.domain.enums.ProductCategory;
import za.co.admatech.domain.enums.ProductType;
import za.co.admatech.factory.*;
import za.co.admatech.repository.CustomerRepository;
import za.co.admatech.repository.OrderRepository;
import za.co.admatech.repository.ProductRepository;
import za.co.admatech.service.order_item_domain_service.OrderItemService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class OrderItemServiceTest {

    @Autowired
    private OrderItemService service;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private final String ORDER_ITEM_ID = "OI101";
    private final Money PRICE = new Money.Builder()
            .amount(new java.math.BigDecimal("1200.00"))
            .currency("ZAR")
            .build();

    Category gamingCategory = new Category.Builder()
            .categoryId("C101")
            .parentCategoryId(null)  // Or some valid parent ID
            .name("Gaming")
            .build();

    private OrderItem createTestOrderItem() {
        Product product = ProductFactory.createProduct(
                "P101", "Keyboard", "Gaming keyboard", PRICE,
                gamingCategory, ProductType.DESKTOP
        );
        productRepository.save(product);
        Address address = AddressFactory.createAddress(401L, "100", "Main Rd", "Suburb", "City", "Province", "1234");

        Customer customer = CustomerFactory.createCustomer(
                "Jane",
                "Smith",
                "jane@example.com",
                address
        );


        customerRepository.save(customer);

        Order order = OrderFactory.createOrder(
                "O101",
                LocalDateTime.now(),  // Use LocalDateTime, not LocalDate
                PRICE,
                customer,
                List.of()  // list of OrderItem(s)
        );

        orderRepository.save(order);

        return OrderItemFactory.createOrderItem(
                ORDER_ITEM_ID, 1, PRICE, order, product
        );
    }

    @Test
    void a_create() {
        OrderItem created = service.create(createTestOrderItem());
        assertNotNull(created);
        assertEquals(ORDER_ITEM_ID, created.getOrderItemId());
        assertEquals(1, created.getQuantity());
        System.out.println("Created: " + created);
    }

    @Test
    void b_read() {
        service.create(createTestOrderItem());
        OrderItem read = service.read(ORDER_ITEM_ID);
        assertNotNull(read);
        assertEquals(ORDER_ITEM_ID, read.getOrderItemId());
        System.out.println("Read: " + read);
    }

    @Test
    void c_update() {
        service.create(createTestOrderItem());
        OrderItem updatedItem = new OrderItem.Builder()
                .copy(service.read(ORDER_ITEM_ID))
                .quantity(3)
                .build();
        OrderItem result = service.update(updatedItem);
        assertNotNull(result);
        assertEquals(3, result.getQuantity());
        System.out.println("Updated: " + result);
    }

    @Test
    void d_delete() {
        service.create(createTestOrderItem());
        boolean deleted = service.delete(ORDER_ITEM_ID);
        assertTrue(deleted);
        assertThrows(EntityNotFoundException.class, () -> service.read(ORDER_ITEM_ID));
        System.out.println("Deleted: " + deleted);
    }

    @Test
    void e_getAll() {
        service.create(createTestOrderItem());
        List<OrderItem> all = service.getAll();
        assertNotNull(all);
        assertFalse(all.isEmpty());
        all.forEach(System.out::println);
    }
}
