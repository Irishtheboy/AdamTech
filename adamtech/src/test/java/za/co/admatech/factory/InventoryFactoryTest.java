/*
 * InventoryFactoryTest.java
 * Author: Teyana Raubenheimer (230237622)
 * Date: 18 May 2025
 */
package za.co.admatech.factory;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import za.co.admatech.domain.Inventory;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Product;
import za.co.admatech.domain.enums.InventoryStatus;
import za.co.admatech.service.InventoryService;
import za.co.admatech.service.ProductService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class InventoryFactoryTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private InventoryService inventoryService; // For optional persistence test

    private Product product1;
    private Product product2;
    private Product product3;
    private Inventory inventory1;
    private Inventory inventory2;
    private Inventory inventory3;

    @BeforeAll
    void setup() {
        // Initialize Products
        product1 = new Product.Builder()
                .setName("Product 1")
                .setDescription("Test product 1")
                .setSku("PROD001")
                .setPrice(new Money.Builder().setAmount(10000).setCurrency("ZAR").build()) // 100.00 ZAR
                .setCategoryId("CAT1")
                .build();

        product2 = new Product.Builder()
                .setName("Product 2")
                .setDescription("Test product 2")
                .setSku("PROD002")
                .setPrice(new Money.Builder().setAmount(20000).setCurrency("ZAR").build()) // 200.00 ZAR
                .setCategoryId("CAT1")
                .build();

        product3 = new Product.Builder()
                .setName("Product 3")
                .setDescription("Test product 3")
                .setSku("PROD003")
                .setPrice(new Money.Builder().setAmount(30000).setCurrency("ZAR").build()) // 300.00 ZAR
                .setCategoryId("CAT1")
                .build();
    }

    @BeforeEach
    void beforeEach() {
        // Persist Products before each test to ensure unique Product instances for @OneToOne
        product1 = productService.create(product1);
        product2 = productService.create(product2);
        product3 = productService.create(product3);

        // Initialize Inventories using Builder (or factory)
        inventory1 = new Inventory.Builder()
                .setProduct(product1)
                .setQuantity(100)
                .setInventoryStatus(InventoryStatus.IN_STOCK)
                .build();

        inventory2 = new Inventory.Builder()
                .setProduct(product2)
                .setQuantity(5)
                .setInventoryStatus(InventoryStatus.LOW_STOCK)
                .build();

        inventory3 = new Inventory.Builder()
                .setProduct(product3)
                .setQuantity(0)
                .setInventoryStatus(InventoryStatus.OUT_OF_STOCK)
                .build();
    }

    @Test
    @Order(1)
    void createInventory() {
        // Test inventory1
        assertNotNull(inventory1);
        assertEquals(100, inventory1.getQuantity());
        assertEquals(InventoryStatus.IN_STOCK, inventory1.getInventoryStatus());
        assertEquals(product1.getProductId(), inventory1.getProduct().getProductId());

        // Test inventory2
        assertNotNull(inventory2);
        assertEquals(5, inventory2.getQuantity());
        assertEquals(InventoryStatus.LOW_STOCK, inventory2.getInventoryStatus());
        assertEquals(product2.getProductId(), inventory2.getProduct().getProductId());

        // Test inventory3
        assertNotNull(inventory3);
        assertEquals(0, inventory3.getQuantity());
        assertEquals(InventoryStatus.OUT_OF_STOCK, inventory3.getInventoryStatus());
        assertEquals(product3.getProductId(), inventory3.getProduct().getProductId());

        System.out.println("Inventory 1: " + inventory1);
        System.out.println("Inventory 2: " + inventory2);
        System.out.println("Inventory 3: " + inventory3);
    }

    @Test
    @Order(2)
    void createInventoryWithInvalidData() {
        // Test with negative quantity
        Inventory invalidInventory = new Inventory.Builder()
                .setProduct(product1)
                .setQuantity(-1)
                .setInventoryStatus(InventoryStatus.IN_STOCK)
                .build();
        assertNotNull(invalidInventory);
        assertEquals(-1, invalidInventory.getQuantity()); // Builder allows negative quantity
        assertEquals(InventoryStatus.IN_STOCK, invalidInventory.getInventoryStatus());
        assertEquals(product1.getProductId(), invalidInventory.getProduct().getProductId());
        System.out.println("Invalid Inventory: " + invalidInventory);
    }

    @Test
    @Order(3)
    void createAndPersistInventory() {
        // Test persistence to ensure factory produces valid objects
        Inventory persisted1 = inventoryService.create(inventory1);
        assertNotNull(persisted1.getId());
        assertEquals(inventory1.getQuantity(), persisted1.getQuantity());
        assertEquals(inventory1.getInventoryStatus(), persisted1.getInventoryStatus());
        assertEquals(inventory1.getProduct().getProductId(), persisted1.getProduct().getProductId());

        Inventory persisted2 = inventoryService.create(inventory2);
        assertNotNull(persisted2.getId());
        assertEquals(inventory2.getQuantity(), persisted2.getQuantity());
        assertEquals(inventory2.getInventoryStatus(), persisted2.getInventoryStatus());
        assertEquals(inventory2.getProduct().getProductId(), persisted2.getProduct().getProductId());

        Inventory persisted3 = inventoryService.create(inventory3);
        assertNotNull(persisted3.getId());
        assertEquals(inventory3.getQuantity(), persisted3.getQuantity());
        assertEquals(inventory3.getInventoryStatus(), persisted3.getInventoryStatus());
        assertEquals(inventory3.getProduct().getProductId(), persisted3.getProduct().getProductId());

        System.out.println("Persisted Inventory 1: " + persisted1);
        System.out.println("Persisted Inventory 2: " + persisted2);
        System.out.println("Persisted Inventory 3: " + persisted3);
    }
}