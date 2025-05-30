/*CartServiceTest.java
  Author: Teyana Raubenheimer (230237622)
  Date: 25 May 2025
 */

package za.co.admatech.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Cart;
import za.co.admatech.factory.CartFactory;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class CartServiceTest {
    @Autowired
    private ICartService service;
    private Cart cart = CartFactory.createCart("123", "1");

    @Test
    void a_create() {
        Cart createdCart = service.create(cart);
        assertNotNull(createdCart);
        System.out.println(createdCart);
    }

    @Test
    void b_read() {
        Cart readCart = service.read(cart.getCartID());
        assertNotNull(readCart);
        System.out.println(readCart);
    }

    @Test
    void c_update() {
        Cart updatedCart = new Cart.Builder().copy(cart).setCartID("2").build();
        Cart updated = service.update(updatedCart);
        assertNotNull(updated);
        System.out.println(updated);
    }

    @Test
    void d_delete() {
        boolean deleted = service.delete(cart.getCartID());
        assertTrue(deleted);
        System.out.println("Cart deleted successfully");
    }

    @Test
    void e_getAll() {
            System.out.println(service.getAll());
        }
    }
