package za.co.admatech.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Product;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    private static Product testProduct;
    private static byte[] imageBytes;

    @BeforeAll
    static void loadImage() throws Exception {
        imageBytes = Files.readAllBytes(Paths.get("src/test/resources/MSIKeyboard.png"));
    }

    @Test
    @Order(1)
    void create() {
        Money price = new Money.Builder()
                .setAmount(1000)
                .setCurrency("USD")
                .build();

        Product product = new Product.Builder()
                .setName("Test Product")
                .setDescription("This is a test product")
                .setSku("TP1000")
                .setPrice(price)
                .setCategoryId("CAT1")
                .setImageData(imageBytes)
                .build();

        Product created = productService.create(product);
        assertNotNull(created);
        assertNotNull(created.getProductId());
        assertEquals("Test Product", created.getName());

        testProduct = created; // save for later tests
    }

    @Test
    @Order(2)
    void read() {
        assertNotNull(testProduct);
        Product found = productService.read(testProduct.getProductId());
        assertNotNull(found);
        assertEquals(testProduct.getName(), found.getName());
    }

    @Test
    @Order(3)
    void update() {
        assertNotNull(testProduct);

        Product updatedProduct = new Product.Builder()
                .copy(testProduct)
                .setName("Updated Product Name")
                .build();

        Product updated = productService.update(updatedProduct);
        assertNotNull(updated);
        assertEquals("Updated Product Name", updated.getName());

        testProduct = updated; // update reference for future tests
    }

    @Test
    @Order(4)
    void getAll() {
        List<Product> allProducts = productService.getAll();
        assertFalse(allProducts.isEmpty());
        assertTrue(allProducts.stream().anyMatch(p -> p.getProductId().equals(testProduct.getProductId())));
    }

    @Test
    @Order(5)
    @Disabled
    void delete() {
        assertNotNull(testProduct);
        boolean deleted = productService.delete(testProduct.getProductId());
        assertTrue(deleted);

        Product deletedProduct = productService.read(testProduct.getProductId());
        assertNull(deletedProduct);
    }
}
