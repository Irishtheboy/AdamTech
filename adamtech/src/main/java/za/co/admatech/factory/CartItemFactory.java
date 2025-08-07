/*Cart.java
  Cart Class
  Author: Teyana Raubenheimer (230237622)
  Date: 14 May 2025
 */

package za.co.admatech.factory;

import za.co.admatech.domain.Cart;
import za.co.admatech.domain.CartItem;
import za.co.admatech.domain.Product;
import za.co.admatech.util.Helper;

public class CartItemFactory {
    public static CartItem createCartItem(String productID, int quantity, String cartID) {
        String cartItemID = Helper.generateId();

        if (Helper.isNullOrEmpty(productID) || quantity <= 0 || Helper.isNullOrEmpty(cartID)) {
            return null;
        }

        return new CartItem.Builder()
               // .setCartItemID(cartItemID)
                .setProductID(productID)
                .setQuantity(quantity)
                .setCartID(cartID)
                .build();
    }

}
