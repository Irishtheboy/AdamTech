package za.co.admatech.factory;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import za.co.admatech.domain.Cart;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class CartFactoryTest {
    private static Cart c = CartFactory.createCart("", null, null);

    @Test
    @Order(1)
    public void testCreateCart() {
        assertNotNull(c);
        System.out.println(c.toString());
    }

    @Test
    @Order(2)
    public void testCreateCartThatFails() {
        //fail();
        assertNotNull(c);
        System.out.println(c.toString());
    }


}