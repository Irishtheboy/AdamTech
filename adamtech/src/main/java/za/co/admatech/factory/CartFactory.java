/*
CartFactory.java
Author: Teyana Raubenheimer (230237622)
Date: 14 May 2025 */
package za.co.admatech.factory;

import za.co.admatech.domain.Cart;
import za.co.admatech.domain.CartItem;
import za.co.admatech.domain.Customer;
import za.co.admatech.util.Helper;
import java.util.List;

public class CartFactory { public static Cart createCart(Long id, Customer customer, List cartItems) {
    if (!Helper.isValidCustomer(customer))
    { throw new IllegalArgumentException("Invalid customer"); }
    if (cartItems == null) { throw new IllegalArgumentException("Cart items list cannot be null"); }

    return new Cart.Builder()
            .setCartID(id)
            .setCustomer(customer)
            .setCartItems(cartItems)
            .build(); } }