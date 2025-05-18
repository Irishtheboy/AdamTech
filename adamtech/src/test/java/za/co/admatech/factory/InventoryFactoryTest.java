package za.co.admatech.factory;

import static org.junit.jupiter.api.Assertions.*;

import za.co.admatech.domain.Inventory;
import za.co.admatech.domain.enums.InventoryStatus;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InventoryFactoryTest {

    private static Inventory i1 = InventoryFactory.createInventory(
            "inv001", "prod001", 100, InventoryStatus.IN_STOCK
    );

    private static Inventory i2 = InventoryFactory.createInventory(
            "inv002", "prod002", 5, InventoryStatus.LOW_STOCK
    );

    private static Inventory i3 = InventoryFactory.createInventory(
            "inv003", "prod003", 0, InventoryStatus.OUT_OF_STOCK
    );

    @Test
    @Order(1)
    public void testCreateInventory1() {
        assertNotNull(i1);
        assertNotNull(i1.getId());
        System.out.println(i1.toString());
    }

    @Test
    @Order(2)
    public void testCreateInventory2() {
        assertNotNull(i2);
        assertNotNull(i2.getId());
        System.out.println(i2.toString());
    }

    @Test
    @Order(3)
    public void testCreateInventory3() {
        assertNotNull(i3);
        assertNotNull(i3.getId());
        System.out.println(i3.toString());
    }
}