/*
 * CartItemFactoryTest.java
 * Author: Teyana Raubenheimer (230237622)
 * Date: 18 May 2025
 */
package za.co.admatech.factory;

import org.junit.jupiter.api.*;
import za.co.admatech.domain.CartItem;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartItemFactoryTest {

    private static CartItem cartItem1 = CartItemFactory.createCartItem(2, "CART123");
    private static CartItem cartItem2 = CartItemFactory.createCartItem(5, "CART456");

    @Test
    @Order(1)
    void testCreateCartItem1() {
        assertNotNull(cartItem1);
        assertEquals(2, cartItem1.getQuantity());
        System.out.println("CartItem 1 created: " + cartItem1);
    }

    @Test
    @Order(2)
    void testCreateCartItem2() {
        assertNotNull(cartItem2);
        assertEquals(5, cartItem2.getQuantity());
        System.out.println("CartItem 2 created: " + cartItem2);
    }

    @Test
    @Order(3)
    void testCreateCartItemWithInvalidData() {
        CartItem invalidCartItem1 = CartItemFactory.createCartItem(-1, "CART123");
        assertNull(invalidCartItem1);
        
        CartItem invalidCartItem2 = CartItemFactory.createCartItem(2, null);
        assertNull(invalidCartItem2);
        
        CartItem invalidCartItem3 = CartItemFactory.createCartItem(0, "");
        assertNull(invalidCartItem3);
        System.out.println("Invalid CartItem creation tests passed");
    }
}