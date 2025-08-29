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

import java.util.List;

import org.junit.jupiter.api.BeforeEach;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class CartFactoryTest {
//    private static CartItem validCartItem = new CartItem.Builder()
//            .setCartItemID("1")
//           // .setProductID()
//            .setQuantity(2)
//            .setCartID(null) // Assuming cartID is optional
//            .build();
//
    private Customer customer;
    private CartItem cartItem1;
    private CartItem cartItem2;
    private CartItem cartItem3;
    private Product product1;
    private Product product2;
    private Product product3;
    private List<CartItem> cartItems;
    private Cart cart;



    @BeforeEach
    void setUp() {
        // Any setup if needed
        customer = new Customer.Builder()
            .setFirstName("Teyana")
            .setLastName("Raubenheimer")
            .build();
        product1 = new Product.Builder(
            ).setName("Product 1")
            .setDescription("Description 1")
            .setSku("SKU1")
            .setPrice(MoneyFactory.createMoney(3000, "ZAR"))
            .setCategoryId("CAT1")
            .build();            
        product2 = new Product.Builder(
            ).setName("Product 2")
            .setDescription("Description 2")
            .setSku("SKU2")
            .setPrice(MoneyFactory.createMoney(3000, "ZAR"))
            .setCategoryId("CAT2")
            .build();
        product3 = new Product.Builder(
            ).setName("Product 3")
            .setDescription("Description 3")
            .setSku("SKU3")
            .setPrice(MoneyFactory.createMoney(3000, "ZAR"))
            .setCategoryId("CAT3")
            .build();
        cartItem1 = new CartItem.Builder()
            .setProduct(product1)
            .setQuantity(2)
            .build();
        cartItem2 = new CartItem.Builder()
            .setProduct(product2)
            .setQuantity(3)
            .build();
        cartItem3 = new CartItem.Builder()
            .setProduct(product3)
            .setQuantity(1)
            .build();
        cartItems = List.of(cartItem1, cartItem2, cartItem3);
        cart = CartFactory.createCart(customer, cartItems);

    }

    @Test
    @Order(1)
    public void testCreateCart() {
        assertNotNull(cart);
        System.out.println(cart.toString());
    }

    @Test
    @Order(2)
    public void testCreateCartThatFails() {
        Cart invalidCart = CartFactory.createCart(null, null);
        assertNull(invalidCart);
    }


}