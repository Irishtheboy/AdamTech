package za.co.admatech.service;/*
InventoryServiceTest.java
Author: Seymour Lawrence (230185991)
Date: 25 May 2025
Description: This test class contains integration tests for the InventoryService class,
verifying the functionality of create, read, update, delete, and getAll methods
using Spring Boot without mocks, including product and status validation.
*/


import jakarta.persistence.EntityNotFoundException;
import za.co.admatech.domain.Inventory;
import za.co.admatech.domain.Product;
import za.co.admatech.domain.enums.InventoryStatus;
import za.co.admatech.factory.ProductFactory;
import za.co.admatech.service.inventory_domain_service.IInventoryService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class InventoryServiceTest {

    @Autowired
    private IInventoryService service;

    private static Inventory validInventory;
    private static Inventory invalidInventory;
    private static Product product;

    @BeforeAll
    public static void setUp() {
        // Create a product
        product = ProductFactory.createProduct(
                "prod-101",
                "Mechanical Keyboard",
                "RGB Gaming Keyboard",
                "SKU-123",
                null, // Assuming Money is optional for this test
                null, // Assuming Category is optional for this test
                null  // Assuming ProductType is optional for this test
        );

        // Create a valid inventory
        validInventory = new Inventory.Builder()
                .inventoryId("inv-101")
                .productId("prod-101")
                .quantity(50)
                .inventoryStatus(InventoryStatus.IN_STOCK)
                .product(product)
                .build();

        // Create an invalid inventory (null or invalid fields)
        invalidInventory = new Inventory.Builder()
                .inventoryId(null)
                .productId("")
                .quantity(-1)
                .inventoryStatus(null)
                .product(null)
                .build();
    }

    @Test
    void a_create() {
        Product savedProduct = null; // Assume product is pre-saved or managed elsewhere
        if (product != null) {
            savedProduct = product; // In a real test, save product first
        }
        validInventory.setProduct(savedProduct); // Ensure product is set
        Inventory created = service.create(validInventory);
        assertNotNull(created);
        assertEquals("inv-101", created.getInventoryId());
        assertEquals("prod-101", created.getProductId());
        assertEquals(50, created.getQuantity());
        assertEquals(InventoryStatus.IN_STOCK, created.getInventoryStatus());
        System.out.println("Created Inventory: " + created);
    }

    @Test
    void b_read() {
        validInventory.setProduct(product); // Ensure product is set
        Inventory saved = service.create(validInventory);
        Inventory read = service.read(saved.getInventoryId());
        assertNotNull(read);
        assertEquals("inv-101", read.getInventoryId());
        assertEquals("prod-101", read.getProductId());
        assertEquals(50, read.getQuantity());
        assertEquals(InventoryStatus.IN_STOCK, read.getInventoryStatus());
        System.out.println("Read Inventory: " + read);
    }

    @Test
    void c_update() {
        validInventory.setProduct(product); // Ensure product is set
        Inventory saved = service.create(validInventory);
        Inventory updated = saved.copy();
        updated.setQuantity(75); // Example update
        updated.setInventoryStatus(InventoryStatus.OUT_OF_STOCK);
        Inventory result = service.update(updated);
        assertNotNull(result);
        assertEquals("inv-101", result.getInventoryId());
        assertEquals(75, result.getQuantity());
        assertEquals(InventoryStatus.OUT_OF_STOCK, result.getInventoryStatus());
        System.out.println("Updated Inventory: " + result);
    }

    @Test
    void d_delete() {
        validInventory.setProduct(product); // Ensure product is set
        Inventory saved = service.create(validInventory);
        boolean deleted = service.delete(saved.getInventoryId());
        assertTrue(deleted);
        assertThrows(EntityNotFoundException.class, () -> service.read(saved.getInventoryId()));
        System.out.println("Deleted: " + deleted);
    }

    @Test
    void e_getAll() {
        validInventory.setProduct(product); // Ensure product is set
        service.create(validInventory);
        List<Inventory> inventories = service.getAll();
        assertNotNull(inventories);
        assertFalse(inventories.isEmpty());
        inventories.forEach(System.out::println);
    }

    @Test
    void f_createInvalid() {
        assertThrows(IllegalArgumentException.class, () -> service.create(invalidInventory));
        System.out.println("Invalid inventory creation test passed");
    }
}