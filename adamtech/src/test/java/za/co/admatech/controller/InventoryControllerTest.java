package za.co.admatech.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Inventory;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Product;
import za.co.admatech.domain.enums.InventoryStatus;
import za.co.admatech.service.IInventoryService;
import za.co.admatech.service.IProductService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class InventoryControllerTest {

    @Autowired
    private IInventoryService inventoryService;

    private static Inventory inventory;
    private static Product product;

    @BeforeAll
    static void setUp(@Autowired IProductService productService) {
        // Persist Product first
        product = new Product.Builder()
                .setName("Test Product")
                .setDescription("Test Description")
                .setPrice(new Money(150, "USD"))
                .build();
        product = productService.create(product);

        // Build Inventory with persisted Product
        inventory = new Inventory.Builder()
                .setProduct(product)
                .setQuantity(10)
                .setInventoryStatus(InventoryStatus.IN_STOCK) // match test assertions
                .build();
    }

    @Test
    void a_create() {
        Inventory created = inventoryService.create(inventory);
        assertNotNull(created);
        assertNotNull(created.getId());
        inventory = created;
        System.out.println("Created Inventory: " + created);
    }

    @Test
    void b_read() {
        Inventory found = inventoryService.read(inventory.getId());
        assertNotNull(found);
        assertEquals(10, found.getQuantity());
        assertEquals(InventoryStatus.IN_STOCK, found.getInventoryStatus()); // corrected
        System.out.println("Read Inventory: " + found);
    }

    @Test
    void c_update() {
        Inventory updatedInventory = new Inventory.Builder()
                .copy(inventory)
                .setQuantity(20)
                .setInventoryStatus(InventoryStatus.OUT_OF_STOCK)
                .build();

        Inventory updated = inventoryService.update(updatedInventory);
        assertNotNull(updated);
        assertEquals(20, updated.getQuantity());
        assertEquals(InventoryStatus.OUT_OF_STOCK, updated.getInventoryStatus());
        inventory = updated;
        System.out.println("Updated Inventory: " + updated);
    }

    @Test
    void d_getAll() {
        List<Inventory> allInventories = inventoryService.getAll();
        assertNotNull(allInventories);
        assertTrue(allInventories.size() >= 1);
        System.out.println("All Inventories:");
        allInventories.forEach(System.out::println);
    }

    @Test
    void e_delete() {
        boolean deleted = inventoryService.delete(inventory.getId());
        assertTrue(deleted);

        Inventory afterDelete = inventoryService.read(inventory.getId());
        assertNull(afterDelete);
        System.out.println("Deleted Inventory: true");
    }
}
