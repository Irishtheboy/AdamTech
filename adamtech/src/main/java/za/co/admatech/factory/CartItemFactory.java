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
    public static CartItem createCartItem(Product productID, int quantity, Cart cartID) {
        String cartItemID = Helper.generateId();

        if (productID == null || quantity <= 0 || cartID == null) {
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
