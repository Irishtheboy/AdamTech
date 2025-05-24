/*Cart.java
  Cart Class
  Author: Teyana Raubenheimer (230237622)
  Date: 18 May 2025
 */

package za.co.admatech.factory;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import za.co.admatech.domain.CartItem;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class CartItemFactoryTest {

    private static CartItem ci = CartItemFactory.createCartItem(null, 5, null);

    @Test
    @Order(1)
    public void testCreateCartItem() {
        assertNotNull(ci);
        System.out.println(ci.toString());

    }

    @Test
    @Order(2)
    public void testCreateCartItemThatFails() {
        //fail();
        assertNotNull(ci);
        System.out.println(ci.toString());
    }
}