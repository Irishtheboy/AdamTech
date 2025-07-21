package za.co.admatech.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.*;
import za.co.admatech.domain.enums.ProductType;
import za.co.admatech.factory.OrderItemFactory;
import za.co.admatech.service.order_item_domain_service.OrderItemService;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class OrderItemServiceTest {

    @Autowired
    private OrderItemService service;

    // Sample test OrderItem created using factory
    private Product product = new Product.Builder()
            .setProductName("Test Item")
            .setProductDescription("Test Desc")
            .setProductPriceAmount(new Money(100, "ZAR"))
            .setProductCategory("GAMING")
            .setProductType(ProductType.PERIPHERAL)
            .build();

    private OrderItem orderItem = OrderItemFactory.createOrderItem(
            987L,
            1,
            new Money(100.00, "ZAR"),
            null,
            product
    );

    @Test
    void a_create() {
        OrderItem createdItem = service.create(orderItem);
        assertNotNull(createdItem);
        assertEquals(orderItem.getQuantity(), createdItem.getQuantity());
        System.out.println("Created: " + createdItem);
    }

    @Test
    void b_read() {
        OrderItem savedItem = service.create(orderItem);
        OrderItem readItem = service.read(savedItem.getId());
        assertNotNull(readItem);
        System.out.println("Read: " + readItem);
    }

    @Test
    void c_update() {
        OrderItem savedItem = service.create(orderItem);
        OrderItem updatedItem = new OrderItem.Builder()
                .copy(savedItem)
                .setQuantity(2)
                .build();
        OrderItem updated = service.update(updatedItem);
        assertNotNull(updated);
        assertEquals(2, updated.getQuantity());
        System.out.println("Updated: " + updated);
    }

    @Test
    void d_delete() {
        OrderItem savedItem = service.create(orderItem);
        boolean deleted = service.delete(savedItem.getId());
        assertTrue(deleted);
        assertNull(service.read(savedItem.getId())); // confirm deletion
        System.out.println("Deleted: " + deleted);
    }

    @Test
    void e_getOrderItems() {
        List<OrderItem> items = service.getOrderItems();
        assertNotNull(items);
        assertFalse(items.isEmpty());
        System.out.println("All OrderItems: " + items);
    }
}
