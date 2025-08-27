package za.co.admatech.factory;

import org.junit.jupiter.api.Test;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Product;

import static org.junit.jupiter.api.Assertions.*;

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
    void buildProduct_ShouldCreateUniqueInstances_WhenCalledMultipleTimes() {
        // Given
        Long productId = 1L;
        String name = "Test Product";
        String description = "Test Description";
        String sku = "TEST-001";
        Money price = MoneyFactory.buildMoney(10000, "ZAR");
        String categoryId = "test";

        // When
        Product product1 = ProductFactory.buildProduct(
                productId, name, description, sku, price, categoryId
        );
        Product product2 = ProductFactory.buildProduct(
                productId, name, description, sku, price, categoryId
        );

        // Then
        assertNotNull(product1);
        assertNotNull(product2);
        assertNotSame(product1, product2); // Different instances
        assertEquals(product1.getProductId(), product2.getProductId()); // Same data
        assertEquals(product1.getName(), product2.getName());
        assertEquals(product1.getSku(), product2.getSku());
    }

    @Test
    void buildProduct_ShouldPreservePriceRelationship() {
        // Given
        Money price = MoneyFactory.buildMoney(250000, "USD");

        // When
        Product product = ProductFactory.buildProduct(
                1L, "MacBook Pro", "Apple laptop", "APPLE-001", price, "computers"
        );

        // Then
        assertNotNull(product.getPrice());
        assertEquals(price.getAmount(), product.getPrice().getAmount());
        assertEquals(price.getCurrency(), product.getPrice().getCurrency());
    }

    @Test
    void buildProduct_ShouldHandleZeroPrice() {
        // Given
        Money freePrice = MoneyFactory.buildMoney(0, "ZAR");

        // When
        Product product = ProductFactory.buildProduct(
                1L, "Free Sample", "Free product sample", "FREE-001", freePrice, "samples"
        );

        // Then
        assertNotNull(product);
        assertNotNull(product.getPrice());
        assertEquals(0, product.getPrice().getAmount());
        assertEquals("ZAR", product.getPrice().getCurrency());
    }
}
