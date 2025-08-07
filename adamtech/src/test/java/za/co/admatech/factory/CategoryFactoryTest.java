package za.co.admatech.factory;

import org.springframework.core.annotation.Order;
import za.co.admatech.domain.*;
import za.co.admatech.factory.CategoryFactory;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryFactoryTest {

    private static Category parent = CategoryFactory.createCategory("1", null, "Parent", null);

    private static Category c1 = CategoryFactory.createCategory("1", null, "Electronics", null);
    private static Category c2 = CategoryFactory.createCategory("2", "1", "SubElectronics", parent);
    private static Category c3 = CategoryFactory.createCategory("3", null, "Clothing", null);

    @Test
    @Order(1)
    public void testCreateCategory1() {
        assertNotNull(c1);
        assertNotNull(c1.getCategoryId());
        System.out.println(c1.toString());
    }

    @Test
    @Order(2)
    public void testCreateCategory2() {
        assertNotNull(c2);
        assertNotNull(c2.getCategoryId());
        System.out.println(c2.toString());
    }

    @Test
    @Order(3)
    public void testCreateCategory3() {
        assertNotNull(c3);
        assertNotNull(c3.getCategoryId());
        System.out.println(c3.toString());
    }
}