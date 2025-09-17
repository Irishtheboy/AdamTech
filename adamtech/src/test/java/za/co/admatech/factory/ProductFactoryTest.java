package za.co.admatech.factory;

import static org.junit.jupiter.api.Assertions.*;

import za.co.admatech.domain.Money;
import za.co.admatech.domain.Product;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

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
        assertNotNull(p1.getProductId());
        System.out.println(p1.toString());
    }

    @Test
    @Order(2)
    public void testCreateProduct2() {
        assertNotNull(p2);
        assertNotNull(p2.getProductId());
        System.out.println(p2.toString());
    }

    @Test
    @Order(3)
    public void testCreateProduct3() {
        assertNotNull(p3);
        assertNotNull(p3.getProductId());
        System.out.println(p3.toString());
    }
}