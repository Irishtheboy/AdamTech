/*





CartItemFactory.java



Author: Teyana Raubenheimer (230237622)



Date: 14 May 2025 */ package za.co.admatech.factory;

import za.co.admatech.domain.Cart; import za.co.admatech.domain.CartItem; import za.co.admatech.domain.Product; import za.co.admatech.util.Helper;

public class CartItemFactory { public static CartItem createCartItem(Long id, Product product, int quantity, Cart cart) { if (product == null || cart == null || quantity <= 0) { throw new IllegalArgumentException("Product, cart, and quantity must be valid"); } if (!Helper.isValidProduct(product) || !Helper.isValidCart(cart)) { throw new IllegalArgumentException("Invalid product or cart"); } return new CartItem.Builder() .setCartItemID(id) .setProduct(product) .setQuantity(quantity) .setCart(cart) .build(); } }