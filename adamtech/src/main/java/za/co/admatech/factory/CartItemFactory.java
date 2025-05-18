package za.co.admatech.factory;

import za.co.admatech.domain.Cart;
import za.co.admatech.domain.CartItem;
import za.co.admatech.util.Helper;

public class CartItemFactory {
    public static CartItem createCartItem(int quantity, Cart cartID) {
        String id = Helper.generateId();

        String productID = Helper.generateId();

        if (quantity <= 0) {
            return null;
        }

        if (cartID == null) {
            return null;
        }

        return new CartItem.Builder()
                .setId(id)
                .setProductID(productID)
                .setQuantity(quantity)
                .setCartID(cartID)
                .build();
    }

}
