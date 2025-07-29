package za.co.admatech.factory;


import za.co.admatech.domain.Cart;
import za.co.admatech.domain.CartItem;
import za.co.admatech.domain.Product;
import za.co.admatech.util.Helper;

public class CartItemFactory {
    public static CartItem createCartItem(String id, String productId, int quantity, Cart cart, Product product) {
        if (Helper.isNullOrEmpty(id) || Helper.isNullOrEmpty(productId) || quantity < 0 || cart == null || product == null) {
            return null;
        }
        return new CartItem.Builder()
                .id(id)
                .productId(productId)
                .quantity(quantity)
                .cart(cart)
                .product(product)
                .build();
    }

    public static CartItem createCartItem(String productId, int quantity, Cart cart, Product product) {
        return createCartItem(Helper.generateId(), productId, quantity, cart, product);
    }
}