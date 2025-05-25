package za.co.admatech.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.CartItem;
import za.co.admatech.factory.CartItemFactory;

import java.sql.SQLOutput;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class CartItemServiceTest {
    @Autowired
    private ICartItemService service;
    private CartItem cartItem = CartItemFactory.createCartItem("123", 10, "1");

    @Test
    void a_create() {
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