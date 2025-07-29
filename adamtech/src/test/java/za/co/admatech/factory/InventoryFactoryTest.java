package za.co.admatech.factory;

import org.springframework.core.annotation.Order;
import za.co.admatech.domain.*;
import za.co.admatech.domain.enums.InventoryStatus;
import za.co.admatech.factory.InventoryFactory;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InventoryFactoryTest {

    private static Product mockProduct = new Product.Builder().productId("prod001").name("Test Product").build();

    private static Inventory i1 = InventoryFactory.createInventory("1", "prod001", 100, InventoryStatus.IN_STOCK, mockProduct);
    private static Inventory i2 = InventoryFactory.createInventory("2", "prod002", 50, InventoryStatus.LOW_STOCK, mockProduct);
    private static Inventory i3 = InventoryFactory.createInventory("3", "prod003", 0, InventoryStatus.OUT_OF_STOCK, mockProduct);

    @Test
    @Order(1)
    public void testCreateInventory1() {
        assertNotNull(i1);
        assertNotNull(i1.getInventoryId());
        System.out.println(i1.toString());
    }

    @Test
    @Order(2)
    public void testCreateInventory2() {
        assertNotNull(i2);
        assertNotNull(i2.getInventoryId());
        System.out.println(i2.toString());
    }

    @Test
    @Order(3)
    public void testCreateInventory3() {
        assertNotNull(i3);
        assertNotNull(i3.getInventoryId());
        System.out.println(i3.toString());
    }
}