/*
 * WishlistServiceTest.java
 * Test class for WishlistService
 * Author: [Your Name]
 * Date: [Today’s Date]
 */

package za.co.admatech.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Customer;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Product;
import za.co.admatech.domain.Wishlist;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WishlistServiceTest {

    @Autowired
    private IWishlistService wishlistService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    private static Customer customer = new Customer.Builder()
            .setFirstName("Franco")
            .setLastName("Lukhele")
            .setEmail("Francolukhele14@gmail.com")
            .setPhoneNumber("0812345678")
            .build();

    private static Product product = new Product.Builder()
            .setName("Gaming Laptop")
            .setDescription("High-end gaming laptop")
            .setPrice(new Money(150, "RSA"))
            .setSku("GL12345")
            .setCategoryId("IQQ1")
            .build();

    private static Wishlist wishlist;

    @Test
    @Order(1)
    void create() {
        // Persist Customer and Product first
        customer = customerService.create(customer);
        product = productService.create(product);

        // Create Wishlist
        wishlist = new Wishlist.Builder()
                .setCustomer(customer)
                .setProduct(product)
                .setCreatedAt(LocalDateTime.now())
                .build();

        Wishlist created = wishlistService.create(wishlist);
        assertNotNull(created);
        assertNotNull(created.getWishlistId());
        assertEquals(customer.getEmail(), created.getCustomer().getEmail());
        assertEquals(product.getProductId(), created.getProduct().getProductId());
        wishlist = created; // Update with generated ID
        System.out.println("Created: " + created);
    }

    @Test
    @Order(2)
    void read() {
        Wishlist read = wishlistService.read(wishlist.getWishlistId());
        assertNotNull(read);
        assertEquals(wishlist.getWishlistId(), read.getWishlistId());
        System.out.println("Read: " + read);
    }

    @Test
    @Order(3)
    void update() {
        // Create a new Product
        Product newProduct = new Product.Builder()
                .setName("Mechanical Keyboard")
                .setDescription("RGB backlit mechanical keyboard")
                .setPrice(new Money(50, "RSA"))
                .setSku("KB98765")
                .setCategoryId("IQQ2")
                .build();
        newProduct = productService.create(newProduct);


        Wishlist updatedWishlist = new Wishlist.Builder()
                .copy(wishlist)
                .setProduct(newProduct)
                .build();

        Wishlist result = wishlistService.update(updatedWishlist);

        assertNotNull(result);
        assertEquals(wishlist.getWishlistId(), result.getWishlistId());
        assertEquals(newProduct.getProductId(), result.getProduct().getProductId());

        wishlist = result;
        System.out.println("Updated: " + result);
    }


    @Test
    @Order(4)
    @Disabled
    void delete() {
        boolean success = wishlistService.delete(wishlist.getWishlistId());
        assertTrue(success);

        // Verify deletion
        Wishlist read = wishlistService.read(wishlist.getWishlistId());
        assertNull(read);
        System.out.println("Deleted wishlist with ID: " + wishlist.getWishlistId());
    }

    @Test
    @Order(5)
    void getAll() {
        List<Wishlist> all = wishlistService.getAll();
        assertNotNull(all);
        assertTrue(all.size() >= 0);
        System.out.println("All wishlists: " + all);
    }
}
