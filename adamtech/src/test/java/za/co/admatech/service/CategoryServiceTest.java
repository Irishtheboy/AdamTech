package za.co.admatech.service;/*
CategoryServiceTest.java
Author: Seymour Lawrence (230185991)
Date: 25 May 2025
Description: This test class contains integration tests for the CategoryService class,
verifying the functionality of create, read, update, delete, and getAll methods
using Spring Boot without mocks, including hierarchical category validation.
*/


import jakarta.persistence.EntityNotFoundException;
import za.co.admatech.domain.Category;
import za.co.admatech.factory.CategoryFactory;
import za.co.admatech.service.category_domain_service.ICategoryService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class CategoryServiceTest {

    @Autowired
    private ICategoryService service;

    private static Category parentCategory;
    private static Category childCategory;
    private static Category invalidCategory;

    @BeforeAll
    public static void setUp() {
        // Create a parent category
        parentCategory = CategoryFactory.createCategory(
                "cat-101",
                null,
                "Electronics",
                null
        );

        // Create a child category
        childCategory = CategoryFactory.createCategory(
                "cat-102",
                "cat-101",
                "Laptops",
                parentCategory
        );

        // Invalid category (null or empty fields)
        invalidCategory = CategoryFactory.createCategory(
                null,
                "",
                "",
                null
        );
    }

    @Test
    void a_createParent() {
        Category created = service.create(parentCategory);
        assertNotNull(created);
        assertEquals("cat-101", created.getCategoryId());
        assertNull(created.getParentCategoryId()); // Top-level category
        assertEquals("Electronics", created.getName());
        System.out.println("Created Parent Category: " + created);
    }

    @Test
    void b_createChild() {
        service.create(parentCategory); // Ensure parent exists
        Category created = service.create(childCategory);
        assertNotNull(created);
        assertEquals("cat-102", created.getCategoryId());
        assertEquals("cat-101", created.getParentCategoryId());
        assertEquals("Laptops", created.getName());
        assertEquals("cat-101", created.getParentCategory().getCategoryId());
        System.out.println("Created Child Category: " + created);
    }

    @Test
    void c_read() {
        service.create(parentCategory);
        Category read = service.read(parentCategory.getCategoryId());
        assertNotNull(read);
        assertEquals("cat-101", read.getCategoryId());
        assertEquals("Electronics", read.getName());
        System.out.println("Read Category: " + read);
    }

    @Test
    void d_update() {
        service.create(parentCategory);
        Category updated = parentCategory.copy();
        updated.setName("Updated Electronics"); // Example update
        Category result = service.update(updated);
        assertNotNull(result);
        assertEquals("cat-101", result.getCategoryId());
        assertEquals("Updated Electronics", result.getName());
        System.out.println("Updated Category: " + result);
    }

    @Test
    void e_delete() {
        service.create(parentCategory);
        boolean deleted = service.delete(parentCategory.getCategoryId());
        assertTrue(deleted);
        assertThrows(EntityNotFoundException.class, () -> service.read(parentCategory.getCategoryId()));
        System.out.println("Deleted: " + deleted);
    }

    @Test
    void f_getAll() {
        service.create(parentCategory);
        service.create(childCategory);
        List<Category> categories = service.getAll();
        assertNotNull(categories);
        assertTrue(categories.size() >= 2); // At least parent and child
        categories.forEach(System.out::println);
    }

    @Test
    void g_createInvalid() {
        assertThrows(IllegalArgumentException.class, () -> service.create(invalidCategory));
        System.out.println("Invalid category creation test passed");
    }
}