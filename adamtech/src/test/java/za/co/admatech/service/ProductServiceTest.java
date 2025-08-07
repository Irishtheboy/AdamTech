package za.co.admatech.service;/*
ProductServiceTest.java
Author: Seymour Lawrence (230185991)
Date: 25 May 2025
Description: This test class contains integration tests for the ProductService class,
verifying the functionality of create, read, update, delete, and getAll methods
using Spring Boot without mocks, including category and SKU validation.
*/


import jakarta.persistence.EntityNotFoundException;
import za.co.admatech.domain.Category;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Product;
import za.co.admatech.domain.enums.ProductType;
import za.co.admatech.factory.CategoryFactory;
import za.co.admatech.factory.MoneyFactory;
import za.co.admatech.factory.ProductFactory;
import za.co.admatech.service.product_domain_service.IProductService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class ProductServiceTest {

    @Autowired
    private IProductService service;

    private static Product validProductWithId;
    private static Product validProductGeneratedId;
    private static Product invalidProduct;
    private static Category validCategory;

    @BeforeAll
    public static void setUp() {
        // Create a top-level category using the new factory method
        validCategory = CategoryFactory.createCategory("Electronics");

        Money price = MoneyFactory.createMoney(
                new BigDecimal("1200.00"),
                "ZAR"
        );

        // Using explicit productId
        validProductWithId = ProductFactory.createProduct(
                "prod-101",
                "Mechanical Keyboard",
                "RGB Gaming Keyboard",
                "SKU-123",
                price,
                validCategory,
                ProductType.PERIPHERAL
        );

        // Using generated productId
        validProductGeneratedId = ProductFactory.createProduct(
                "Wireless Mouse",
                "High-precision Mouse",
                "SKU-124",
                price,
                validCategory,
                ProductType.PERIPHERAL
        );

        // Invalid product (null or empty fields)
        invalidProduct = ProductFactory.createProduct(
                null,
                "",
                null,
                "SKU-125",
                null,
                null,
                null
        );
    }

    @Test
    void a_createWithId() {
        Product created = service.create(validProductWithId);
        assertNotNull(created);
        assertEquals("prod-101", created.getProductId());
        assertEquals("Mechanical Keyboard", created.getName());
        assertEquals("SKU-123", created.getSku());
        assertEquals(validCategory.getCategoryId(), created.getCategory().getCategoryId());
        System.out.println("Created Product with ID: " + created);
    }

    @Test
    void b_createWithGeneratedId() {
        Product created = service.create(validProductGeneratedId);
        assertNotNull(created);
        assertNotNull(created.getProductId()); // Generated ID should not be null
        assertEquals("Wireless Mouse", created.getName());
        assertEquals("SKU-124", created.getSku());
        assertEquals(validCategory.getCategoryId(), created.getCategory().getCategoryId());
        System.out.println("Created Product with Generated ID: " + created);
    }

    @Test
    void c_read() {
        Product saved = service.create(validProductWithId);
        Product read = service.read(saved.getProductId());
        assertNotNull(read);
        assertEquals("prod-101", read.getProductId());
        assertEquals("Mechanical Keyboard", read.getName());
        assertEquals("SKU-123", read.getSku());
        System.out.println("Read Product: " + read);
    }

    @Test
    void d_update() {
        Product saved = service.create(validProductWithId);
        Product updated = saved.copy();
        updated.setName("Updated Mechanical Keyboard"); // Example update
        updated.setSku("SKU-126"); // Update SKU
        Product result = service.update(updated);
        assertNotNull(result);
        assertEquals("prod-101", result.getProductId());
        assertEquals("Updated Mechanical Keyboard", result.getName());
        assertEquals("SKU-126", result.getSku());
        System.out.println("Updated Product: " + result);
    }

    @Test
    void e_delete() {
        Product saved = service.create(validProductWithId);
        boolean deleted = service.delete(saved.getProductId());
        assertTrue(deleted);
        assertThrows(EntityNotFoundException.class, () -> service.read(saved.getProductId()));
        System.out.println("Deleted: " + deleted);
    }

    @Test
    void f_getAll() {
        service.create(validProductWithId);
        List<Product> products = service.getAll();
        assertNotNull(products);
        assertFalse(products.isEmpty());
        products.forEach(System.out::println);
    }

    @Test
    void g_createInvalid() {
        assertThrows(IllegalArgumentException.class, () -> service.create(invalidProduct));
        System.out.println("Invalid product creation test passed");
    }
}