package za.co.admatech.factory;

import org.springframework.core.annotation.Order;
import za.co.admatech.domain.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartItemFactoryTest {

    private static Cart cart = new Cart.Builder().id("1").build();
    private static Product product = new Product.Builder().productId("1").build();

    private static CartItem ci1 = CartItemFactory.createCartItem("1", "prod001", 2, cart, product);
    private static CartItem ci2 = CartItemFactory.createCartItem("2", "prod002", 1, cart, product);
    private static CartItem ci3 = CartItemFactory.createCartItem("3", "prod003", 3, cart, product);

    @Test
    @Order(1)
    public void testCreateCartItem1() {
        assertNotNull(ci1);
        assertNotNull(ci1.getId());
        System.out.println(ci1.toString());
    }

    @Test
    @Order(2)
    public void testCreateCartItem2() {
        assertNotNull(ci2);
        assertNotNull(ci2.getId());
        System.out.println(ci2.toString());
    }

    @Test
    @Order(3)
    public void testCreateCartItem3() {
        assertNotNull(ci3);
        assertNotNull(ci3.getId());
        System.out.println(ci3.toString());
    }
}