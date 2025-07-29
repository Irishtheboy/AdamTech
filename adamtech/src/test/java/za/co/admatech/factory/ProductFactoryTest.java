package za.co.admatech.factory;

import org.springframework.core.annotation.Order;
import za.co.admatech.domain.*;
import za.co.admatech.domain.enums.ProductType;
import za.co.admatech.factory.ProductFactory;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductFactoryTest {

    private static Money price1 = new Money.Builder().amount(new BigDecimal("1000")).currency("ZAR").build();
    private static Money price2 = new Money.Builder().amount(new BigDecimal("750")).currency("ZAR").build();
    private static Money price3 = new Money.Builder().amount(new BigDecimal("0")).currency("ZAR").build();

    private static Category category = new Category.Builder().categoryId("1").name("Components").build();

    private static Product p1 = ProductFactory.createProduct("1", "Sneakers", "White running shoes", "SKU001", price1, category, ProductType.PERIPHERAL);
    private static Product p2 = ProductFactory.createProduct("2", "Boots", "Leather boots", "SKU002", price2, category, ProductType.DESKTOP);
    private static Product p3 = ProductFactory.createProduct("3", "Sandals", "Beach sandals", "SKU003", price3, category, ProductType.DESKTOP);

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