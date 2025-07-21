/*Cart.java
  Cart Class
  Author: Teyana Raubenheimer (230237622)
  Date: 18 May 2025
 */

package za.co.admatech.factory;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Cart;
import za.co.admatech.domain.CartItem;
import za.co.admatech.domain.Customer;
import za.co.admatech.domain.Product;
import za.co.admatech.util.Helper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartFactoryTest {
    Cart c = CartFactory.createCart(
            324l,
            null,
            List.of()
    );
    @Test
    @Order(1)
    public void testCreateCart() {
        assertNotNull(c);
        System.out.println(c.toString());
    }

    @Test
    @Order(2)
    public void testCreateCartThatFails() {
        Cart invalidCart = CartFactory.createCart(23l, null, List.of());
        assertNull(invalidCart);
    }


}