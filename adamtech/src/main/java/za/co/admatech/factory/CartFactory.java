/*Cart.java
  Cart Class
  Author: Teyana Raubenheimer (230237622)
  Date: 14 May 2025
 */

package za.co.admatech.factory;

import za.co.admatech.domain.Cart;
import za.co.admatech.domain.CartItem;
import za.co.admatech.domain.Customer;
import za.co.admatech.util.Helper;

import java.util.List;

public class CartFactory {
    public static Cart createCart(Long cartID, Customer customer, List<CartItem> cartItems) {
        if(Helper.isValidCustomer(customer)){
            return null;
        }
        if(Helper.isValidCartID(cartID)){
            return null;
        }
        return new Cart.Builder()
                .setCartID(cartID)
                .setCustomer(customer)
                .setCartItems(cartItems)
                .build();
    }
}
