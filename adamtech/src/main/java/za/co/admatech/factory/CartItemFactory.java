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
    public static CartItem createCartItem(Long cartItemID,
                                          String productID,
                                          int quantity,
                                          String cartID,
                                          Cart cart) {

        if(Helper.isNullOrEmpty(productID)){
            return null;
        }
        if(Helper.isNullOrEmpty(cartID)){
            return null;
        }
        return new CartItem.Builder()
                .setCartItemID(cartItemID)
                .setProductID(productID)
                .setQuantity(quantity)
                .setCartID(cartID)
                .build();
    }

}
