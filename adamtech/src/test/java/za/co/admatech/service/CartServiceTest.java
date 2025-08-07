/*CartServiceTest.java
  Author: Teyana Raubenheimer (230237622)
  Date: 25 May 2025
 */

package za.co.admatech.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Cart;
import za.co.admatech.factory.CartFactory;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartServiceTest {
    @Autowired
    private ICartService service;
    private Cart cart = CartFactory.createCart("123", "1");

   // Cart createdCart;

    @Test
    @Order(1)
    void a_create() {
        Cart createdCart = service.create(cart);
        assertNotNull(createdCart);
        System.out.println(createdCart);
    }

    @Test
    @Order(2)
    void b_read() {
        Cart readCart = service.read(cart.getCartID());
        assertNotNull(readCart);
        System.out.println(readCart);
    }

    @Test
    @Order(3)
    void c_update() {
        Cart updatedCart = new Cart.Builder().copy(cart).setCustomerID("2").build();
        Cart updated = service.update(updatedCart);
        assertNotNull(updated);
        System.out.println(updated);
    }

    @Test
    @Order(4)
    void d_delete() {
        boolean deleted = service.delete(cart.getCartID());
        assertTrue(deleted);
        System.out.println("Cart deleted successfully");
    }

    @Test
    @Order(5)
    void e_getAll() {
            System.out.println(service.getAll());
        }
    }
