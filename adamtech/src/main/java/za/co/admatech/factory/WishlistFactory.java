/*
 * WishlistFactory.java
 * Factory class for Wishlist
 * Author: [Your Name]
 * Date: [Today’s Date]
 */

package za.co.admatech.factory;

import za.co.admatech.domain.Customer;
import za.co.admatech.domain.Product;
import za.co.admatech.domain.Wishlist;
import za.co.admatech.util.Helper;

import java.time.LocalDateTime;

public class WishlistFactory {

    public static Wishlist createWishlist(Customer customer, Product product, LocalDateTime createdAt) {

        if (!Helper.isValidLocalDateTime(createdAt)) {
            return null;
        }

        if (customer == null) {
            return null;
        }

        if (product == null) {
            return null;
        }

        return new Wishlist.Builder()
                .customer(customer)
                .product(product)
                .createdAt(createdAt)
                .build();
    }
}
