package za.co.admatech.factory;

import org.springframework.core.annotation.Order;
import za.co.admatech.domain.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartFactoryTest {

    private static Customer customer = new Customer.Builder().customerId("1").build();
    private static List<CartItem> items = new ArrayList<>();

    private static Cart c1 = CartFactory.createCart("1", "cust001", items, customer);
    private static Cart c2 = CartFactory.createCart("2", "cust002", items, customer);
    private static Cart c3 = CartFactory.createCart("3", "cust003", items, customer);

    @Test
    @Order(1)
    public void testCreateCart1() {
        assertNotNull(c1);
        assertNotNull(c1.getId());
        System.out.println(c1.toString());
    }

    @Test
    @Order(2)
    public void testCreateCart2() {
        assertNotNull(c2);
        assertNotNull(c2.getId());
        System.out.println(c2.toString());
    }

    @Test
    @Order(3)
    public void testCreateCart3() {
        assertNotNull(c3);
        assertNotNull(c3.getId());
        System.out.println(c3.toString());
    }
}