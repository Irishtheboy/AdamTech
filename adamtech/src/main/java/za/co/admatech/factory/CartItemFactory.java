package za.co.admatech.factory;

import za.co.admatech.domain.Cart;
import za.co.admatech.domain.CartItem;
import za.co.admatech.util.Helper;

public class CartItemFactory {
    public static CartItem createCartItem(String id, String productID, int quantity, Cart cartID) {
        id = Helper.generateId();

        productID = Helper.generateId();

//        if (quantity <= 0) {
//            return null;
//        }
//
//        if (cartID == null) {
//            return null;
//        }

        return new CartItem.Builder()
                .setId(id)
                .setProductID(productID)
                .setQuantity(quantity)
                .setCartID(cartID)
                .build();
    }

}
