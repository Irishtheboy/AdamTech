package za.co.admatech.factory;

import org.junit.jupiter.api.Test;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Product;


class ProductFactoryTest {

    @Test
    void buildProduct_ShouldReturnValidProduct_WhenAllParametersProvided() {
        // Given
        Long productId = 1L;
        String name = "Gaming Laptop";
        String description = "High-performance gaming laptop";
        String sku = "LAPTOP-001";
        Money price = MoneyFactory.buildMoney(150000, "ZAR");
        String categoryId = "electronics";

        // When
        Product product = ProductFactory.buildProduct(
                productId, name, description, sku, price, categoryId
        );

        // Then
        assertNotNull(product);
        assertEquals(productId, product.getProductId());
        assertEquals(name, product.getName());
        assertEquals(description, product.getDescription());
        assertEquals(sku, product.getSku());
        assertEquals(price, product.getPrice());
        assertEquals(categoryId, product.getCategoryId());
    }

    @Test
    void buildProduct_ShouldHandleNullValues_WhenOptionalParametersNotProvided() {
        // Given
        Long productId = 2L;
        String name = "Smartphone";
        String description = null;
        String sku = "PHONE-001";
        Money price = null;
        String categoryId = null;

        // When
        Product product = ProductFactory.buildProduct(
                productId, name, description, sku, price, categoryId
        );

        // Then
        assertNotNull(product);
        assertEquals(productId, product.getProductId());
        assertEquals(name, product.getName());
        assertNull(product.getDescription());
        assertEquals(sku, product.getSku());
        assertNull(product.getPrice());
        assertNull(product.getCategoryId());
    }

    @Test
    @Order(1)
    public void testCreateProduct1() {
        assertNotNull(p1);
        assertEquals("Sneakers", p1.getName());
        assertEquals("White running shoes", p1.getDescription());
        assertEquals("SKU123", p1.getSku());
        assertEquals(price1, p1.getPrice());
        assertEquals("cat001", p1.getCategoryId());
        System.out.println("Product 1 created: " + p1.toString());
    }

    @Test
    @Order(2)
    public void testCreateProduct2() {
        assertNotNull(p2);
        assertEquals("Boots", p2.getName());
        assertEquals("Leather boots", p2.getDescription());
        assertEquals("SKU456", p2.getSku());
        assertEquals(price2, p2.getPrice());
        assertEquals("cat002", p2.getCategoryId());
        System.out.println("Product 2 created: " + p2.toString());
    }

    @Test
    @Order(3)
    public void testCreateProduct3() {
        assertNotNull(p3);
        assertEquals("Sandals", p3.getName());
        assertEquals("Beach sandals", p3.getDescription());
        assertEquals("SKU789", p3.getSku());
        assertEquals(price3, p3.getPrice());
        assertEquals("cat003", p3.getCategoryId());
        System.out.println("Product 3 created: " + p3.toString());
    }
}
