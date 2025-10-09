package za.co.admatech.factory;

import static org.junit.jupiter.api.Assertions.*;

import za.co.admatech.domain.Money;
import za.co.admatech.domain.Product;
import org.junit.jupiter.api.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductFactoryTest {

    private static Money price1 = new Money.Builder().setAmount(1000).setCurrency("ZAR").build();
    private static Money price2 = new Money.Builder().setAmount(750).setCurrency("ZAR").build();
    private static Money price3 = new Money.Builder().setAmount(0).setCurrency("ZAR").build();

    private static Product p1 = ProductFactory.createProduct(
             "Sneakers", "White running shoes", "SKU123", price1, "cat001"
    );

    private static Product p2 = ProductFactory.createProduct(
             "Boots", "Leather boots", "SKU456", price2, "cat002"
    );

    private static Product p3 = ProductFactory.createProduct(
             "Sandals", "Beach sandals", "SKU789", price3, "cat003"
    );

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