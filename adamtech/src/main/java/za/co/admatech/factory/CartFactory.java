package za.co.admatech.factory;


import za.co.admatech.domain.Cart;
import za.co.admatech.domain.CartItem;
import za.co.admatech.domain.Customer;
import za.co.admatech.util.Helper;

import java.util.List;

public class CartFactory {
    public static Cart createCart(String id, String customerId, List<CartItem> items, Customer customer) {
        if (Helper.isNullOrEmpty(id) || Helper.isNullOrEmpty(customerId) || items == null || customer == null) {
            return null;
        }
        return new Cart.Builder()
                .id(id)
                .customerId(customerId)
                .items(items)
                .customer(customer)
                .build();
    }

    public static Cart createCart(String customerId, List<CartItem> items, Customer customer) {
        return createCart(Helper.generateId(), customerId, items, customer);
    }
}