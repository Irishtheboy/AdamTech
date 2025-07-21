package za.co.admatech.factory;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Product;
import org.junit.jupiter.api.*;
import za.co.admatech.domain.enums.ProductType;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductFactoryTest {

    private static Money price1 = new Money.Builder().setAmount(1000).setCurrency("ZAR").build();
    private static Money price2 = new Money.Builder().setAmount(750).setCurrency("ZAR").build();
    private static Money price3 = new Money.Builder().setAmount(0).setCurrency("ZAR").build();

    private static Product p1 = ProductFactory.createProduct(
            001l, "Sneakers", "White running shoes", price1, "SKU123", ProductType.PERIPHERAL
    );

    private static Product p2 = ProductFactory.createProduct(
            002l, "Boots", "Leather boots", price2,"SKU456", ProductType.DESKTOP
    );

    private static Product p3 = ProductFactory.createProduct(
            003l, "Sandals", "Beach sandals", price3,"SKU789", ProductType.DESKTOP
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