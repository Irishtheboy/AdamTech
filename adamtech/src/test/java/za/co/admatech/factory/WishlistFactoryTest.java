/*
 * WishlistFactoryTest.java
 * Author: [Your Name] ([Your Student Number])
 * Date: [Date]
 */

package za.co.admatech.factory;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;
import za.co.admatech.domain.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WishlistFactoryTest {

    private static Address address1 = new Address.Builder()
            .setStreetNumber((short) 123)
            .setStreetName("Main Street")
            .setSuburb("Central")
            .setCity("Johannesburg")
            .setProvince("Gauteng")
            .setPostalCode((short) 2000)
            .build();

    private static Money price1 = new Money.Builder().setAmount(1200).setCurrency("ZAR").build();
    private static Money price2 = new Money.Builder().setAmount(500).setCurrency("ZAR").build();

    private static Product product1 = ProductFactory.createProduct(
            "Laptop Bag", "15-inch waterproof laptop bag", "SKU111", price1, "cat010"
    );

    private static Product product2 = ProductFactory.createProduct(
            "Wireless Mouse", "Ergonomic wireless mouse", "SKU222", price2, "cat011"
    );

    private static Customer customer1 = CustomerFactory.createCustomer(
            "Franco", "Lukhele", "Francolukhele14@gmail.com", "password123", address1
    );

    private static Wishlist w1 = WishlistFactory.createWishlist(customer1, product1,LocalDateTime.now());
    private static Wishlist w2 = WishlistFactory.createWishlist(customer1, product2, LocalDateTime.now());

    @Test
    @Order(1)
    void testCreateWishlist1() {
        assertNotNull(w1);
        assertEquals(customer1, w1.getCustomer());
        assertEquals(product1, w1.getProduct());
        assertNotNull(w1.getCreatedAt());
        System.out.println("Wishlist 1 created: " + w1.toString());
    }

    @Test
    @Order(2)
    void testCreateWishlist2() {
        assertNotNull(w2);
        assertEquals(customer1, w2.getCustomer());
        assertEquals(product2, w2.getProduct());
        assertNotNull(w2.getCreatedAt());
        System.out.println("Wishlist 2 created: " + w2.toString());
    }
}
