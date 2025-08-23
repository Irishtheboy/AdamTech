package za.co.admatech.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Inventory;
import za.co.admatech.domain.enums.InventoryStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InventoryServiceTest {

    @Autowired
    private InventoryService inventoryService;

    private static Inventory testInventory;

    @Test
    @Order(1)
    void create() {
        Inventory inventory = new Inventory.Builder()
                .setProductId("PROD123")
                .setQuantity(50)
                .setInventoryStatus(InventoryStatus.IN_STOCK)
                .build();

        Inventory created = inventoryService.create(inventory);
        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals("PROD123", created.getProductId());
        assertEquals(50, created.getQuantity());
        assertEquals(InventoryStatus.IN_STOCK, created.getInventoryStatus());

        testInventory = created; // Save for later tests
    }

    @Test
    @Order(2)
    void read() {
        assertNotNull(testInventory);

        Inventory found = inventoryService.read(testInventory.getId());
        assertNotNull(found);
        assertEquals(testInventory.getProductId(), found.getProductId());
    }

    @Test
    @Order(3)
    void update() {
        assertNotNull(testInventory);

        Inventory updatedInventory = new Inventory.Builder()
                .copy(testInventory)
                .setQuantity(75)
                .setInventoryStatus(InventoryStatus.LOW_STOCK)
                .build();

        Inventory updated = inventoryService.update(updatedInventory);
        assertNotNull(updated);
        assertEquals(75, updated.getQuantity());
        assertEquals(InventoryStatus.LOW_STOCK, updated.getInventoryStatus());

        testInventory = updated; // update reference for next tests
    }

    @Test
    @Order(4)
    void getAll() {
        List<Inventory> allInventories = inventoryService.getAll();
        assertFalse(allInventories.isEmpty());
        assertTrue(allInventories.stream().anyMatch(inv -> inv.getId().equals(testInventory.getId())));
    }

    @Test
    @Order(5)
    @Disabled
    void delete() {
        assertNotNull(testInventory);

        boolean deleted = inventoryService.delete(testInventory.getId());
        assertTrue(deleted);

        Inventory deletedInventory = inventoryService.read(testInventory.getId());
        assertNull(deletedInventory);
    }
}
