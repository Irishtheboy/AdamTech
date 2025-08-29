/*Cart.java
  Cart Class
  Author: Teyana Raubenheimer (230237622)
  Date: 14 May 2025
 */

package za.co.admatech.factory;

import java.util.ArrayList;
import java.util.List;

import za.co.admatech.domain.Cart;
import za.co.admatech.domain.CartItem;
import za.co.admatech.domain.Customer;
import za.co.admatech.util.Helper;

public class CartFactory {
  public static Cart createCart(Customer customer, List<CartItem> cartItems) {

    return new Cart.Builder()
        .setCustomer(customer)
        .setCartItems(cartItems)
        .build();

  }
}
