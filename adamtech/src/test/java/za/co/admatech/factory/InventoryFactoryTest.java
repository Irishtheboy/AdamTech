/*
 * InventoryFactoryTest.java
 * Author: Teyana Raubenheimer (230237622)
 * Date: 18 May 2025
 */
package za.co.admatech.factory;

import org.junit.jupiter.api.*;
import za.co.admatech.domain.Inventory;
import za.co.admatech.domain.enums.InventoryStatus;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InventoryFactoryTest {

    private static Inventory inventory1 = InventoryFactory.createInventory(100, InventoryStatus.IN_STOCK);
    private static Inventory inventory2 = InventoryFactory.createInventory(5, InventoryStatus.LOW_STOCK);
    private static Inventory inventory3 = InventoryFactory.createInventory(0, InventoryStatus.OUT_OF_STOCK);

    @Test
    @Order(1)
    void testCreateInventory1() {
        assertNotNull(inventory1);
        assertEquals(100, inventory1.getQuantity());
        assertEquals(InventoryStatus.IN_STOCK, inventory1.getInventoryStatus());
        System.out.println("Inventory 1 created: " + inventory1);
    }

    @Test
    @Order(2)
    void testCreateInventory2() {
        assertNotNull(inventory2);
        assertEquals(5, inventory2.getQuantity());
        assertEquals(InventoryStatus.LOW_STOCK, inventory2.getInventoryStatus());
        System.out.println("Inventory 2 created: " + inventory2);
    }

    @Test
    @Order(3)
    void testCreateInventory3() {
        assertNotNull(inventory3);
        assertEquals(0, inventory3.getQuantity());
        assertEquals(InventoryStatus.OUT_OF_STOCK, inventory3.getInventoryStatus());
        System.out.println("Inventory 3 created: " + inventory3);
    }

    @Test
    @Order(4)
    void testCreateInventoryWithInvalidData() {
        Inventory validInventory = InventoryFactory.createInventory(-1, InventoryStatus.IN_STOCK);
        assertNotNull(validInventory);
        assertEquals(-1, validInventory.getQuantity());
        assertEquals(InventoryStatus.IN_STOCK, validInventory.getInventoryStatus());
        System.out.println("Inventory with negative quantity created: " + validInventory);
    }
}