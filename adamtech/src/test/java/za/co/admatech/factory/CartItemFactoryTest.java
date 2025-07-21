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
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Cart;
import za.co.admatech.domain.CartItem;
import za.co.admatech.domain.Product;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartItemFactoryTest {
    public CartItem cartItem = CartItemFactory.createCartItem(
            235l,
            "S5858S",
            34,
            "RIRS22",
            null
    );
    @Test
    @Order(1)
    public void testCreateCartItem() {
        assertNotNull(cartItem);
        System.out.println(cartItem.toString());

    }

    @Test
    @Order(2)
    public void testCreateCartItemThatFails() {
        //fail();
        assertNotNull(cartItem);
        System.out.println(cartItem.toString());
    }
}