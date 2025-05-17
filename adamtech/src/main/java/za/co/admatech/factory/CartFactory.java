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

public class CartFactory {
    public static Cart createCart(Customer customerID, CartItem cartItemID) {
      String cartID =  Helper.generateId();

       if (customerID == null) {
              return null;
       }

         if (cartItemID == null) {
             return null;
         }

            return new Cart.Builder()
                    .setCartID(cartID)
                    .setCustomerID(customerID)
                    .setCartItemID(cartItemID)
                    .build();


}
    }
