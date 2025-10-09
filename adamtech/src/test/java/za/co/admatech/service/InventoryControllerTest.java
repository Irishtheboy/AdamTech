package za.co.admatech.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Inventory;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Product;
import za.co.admatech.domain.enums.InventoryStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InventoryControllerTest {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ProductService productService;

    private Product product;
    private Inventory testInventory;

    @BeforeAll
    void setupAll() {
        product = new Product.Builder()
                .setName("Test Product")
                .setDescription("A test product")
                .setSku("PROD123")
                .setPrice(new Money.Builder().setAmount(10000).setCurrency("ZAR").build())
                .setCategoryId("CAT1")
                .build();
        product = productService.create(product);

        testInventory = new Inventory.Builder()
                .setProduct(product)
                .setQuantity(50)
                .setInventoryStatus(InventoryStatus.IN_STOCK)
                .build();
        testInventory = inventoryService.create(testInventory); // persist once
    }

    @Test
    @Order(1)
    void create() {
        assertNotNull(testInventory.getId());
        System.out.println("Created: " + testInventory);
    }

    @Test
    @Order(2)
    void read() {
        Inventory found = inventoryService.read(testInventory.getId());
        assertNotNull(found);
        System.out.println("Read: " + found);
    }

    @Test
    @Order(3)
    void update() {
        Inventory updatedInventory = new Inventory.Builder()
                .copy(testInventory)
                .setQuantity(75)
                .setInventoryStatus(InventoryStatus.LOW_STOCK)
                .build();

        Inventory updated = inventoryService.update(updatedInventory);
        assertEquals(75, updated.getQuantity());
        testInventory = updated;
        System.out.println("Updated: " + updated);
    }

    @Test
    @Order(4)
    void getAll() {
        List<Inventory> all = inventoryService.getAll();
        assertTrue(all.size() >= 1);
        System.out.println("All: " + all);
    }

    @Test
    @Order(5)
    @Disabled
    void delete() {
        boolean deleted = inventoryService.delete(testInventory.getId());
        assertTrue(deleted);
        System.out.println("Deleted ID: " + testInventory.getId());
    }
}
