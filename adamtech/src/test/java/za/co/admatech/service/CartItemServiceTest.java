/*CartItemServiceTest.java
  Author: Teyana Raubenheimer (230237622)
  Date: 24 May 2025
 */

package za.co.admatech.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.CartItem;
import za.co.admatech.factory.CartItemFactory;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class CartItemServiceTest {

    @Autowired
    private ICartItemService service;

    private static CartItem cartItem;

    @Test
    void a_create() {
        CartItem newItem = CartItemFactory.createCartItem("123", 10, "1");
        CartItem createdItem = service.create(newItem);
        assertNotNull(createdItem);
        assertNotNull(createdItem.getCartItemID());  // ID should now be generated
        cartItem = createdItem; // save for later tests
        System.out.println(createdItem);
    }

    @Test
    void b_read() {
        assertNotNull(cartItem);  // ensure previous test ran and set this
        CartItem readItem = service.read(cartItem.getCartItemID());
        assertNotNull(readItem);
        System.out.println(readItem);
    }

    @Test
    void c_update() {
        CartItem updatedItem = new CartItem.Builder()
                .copy(cartItem)
                .setQuantity(3)
                .build();
        CartItem updated = service.update(updatedItem);
        assertNotNull(updated);
        System.out.println(updated);
    }

    @Test
    void d_delete() {
        assertNotNull(cartItem);
        boolean deleted = service.delete(cartItem.getCartItemID());
        assertTrue(deleted);
        System.out.println(deleted);
    }

    @Test
    void e_getAll() {
        System.out.println(service.getAll());
    }
}
