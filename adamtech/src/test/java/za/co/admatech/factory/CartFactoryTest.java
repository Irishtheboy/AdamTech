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
import za.co.admatech.domain.Cart;
import za.co.admatech.domain.CartItem;
import za.co.admatech.domain.Customer;
import za.co.admatech.domain.Product;
import za.co.admatech.util.Helper;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class CartFactoryTest {
    private static CartItem validCartItem = new CartItem.Builder()
            .setCartItemID("1")
           // .setProductID()
            .setQuantity(2)
            .setCartID(null) // Assuming cartID is optional
            .build();

    private static Customer validCustomer = new Customer.Builder()
            .setCustomerID("101")
            .setFirstName("Teyana")
            .setLastName("Raubenheimer")
            .build();


    private static Cart c = CartFactory.createCart(validCustomer, validCartItem);

    @Test
    @Order(1)
    public void testCreateCart() {
        assertNotNull(c);
        System.out.println(c.toString());
    }

    @Test
    @Order(2)
    public void testCreateCartThatFails() {
        Cart invalidCart = CartFactory.createCart(null, null);
        assertNull(invalidCart);
    }


}