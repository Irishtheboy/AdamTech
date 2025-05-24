package za.co.admatech.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import za.co.admatech.domain.CartItem;
import za.co.admatech.factory.CartItemFactory;

import java.sql.SQLOutput;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.MethodName.class)
class CartItemServiceTest {

    private ICartItemService service;
    private CartItem cartItem = CartItemFactory.createCartItem(null, 2, null);

    @Test
    void a_creat() {
        CartItem createdItem = service.create(cartItem);
        assertNotNull(createdItem);
        System.out.println(createdItem);
    }

    @Test
    void b_read() {
        CartItem readItem = service.read(cartItem.getCartItemID());
        assertNotNull(readItem);
        System.out.println(readItem);
    }

    @Test
    void c_update() {
        CartItem newItem = new CartItem.Builder().copy(cartItem).setQuantity(3).build();
        CartItem updated = service.update(newItem);
        assertNotNull(updated);
        System.out.println(updated);
    }

    @Test
    void d_delete() {
        boolean deleted = service.delete(cartItem.getCartItemID());
        assertTrue(deleted);
        System.out.println(deleted);
    }

    @Test
    void e_getAll() {
        System.out.println(service.getAll());
    }

}