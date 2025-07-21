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
import za.co.admatech.domain.*;
import za.co.admatech.domain.enums.OrderStatus;
import za.co.admatech.factory.CartFactory;
import za.co.admatech.factory.CustomerFactory;
import za.co.admatech.service.cart_domain_service.ICartService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class CartServiceTest {
    @Autowired
    private ICartService service;

    Cart cart = new Cart.Builder()
            .setCartID(001L)
            .setCartItems(List.of()) // Empty for now
            .build();

    Address address1 = new Address.Builder()
            .setAddressID(002L)
            .setStreetNumber((short) 101)
            .setStreetName("Tech Avenue")
            .setCity("Cape Town")
            .setProvince("Western Cape")
            .setPostalCode((short) 8001)
            .build();

    Order order1 = new Order.Builder()
            .setId(001L)
            .setOrderDate(LocalDate.of(2025, 5, 24))
            .setOrderStatus(OrderStatus.CONFIRMED)
            .setTotalAmount(new Money(3500, "ZAR"))
            .setOrderItems(List.of())
            .build();

    Customer customer2 = CustomerFactory.createCustomer(
            123L,
            "Rorisang",
            "Makgana",
            "rorisangmmm@gmail.com",
            "0983331111",
            cart,
            List.of(address1),
            List.of(order1)
    );

    Customer customer1 = new Customer.Builder()
                .setCustomerID(001L)
                .setFirstName("Rorisang")
                .setLastName("Makgana")
                .setEmail("rorisang@example.com")
                .setPhoneNumber("0821234567")
                .setCart(cart)
                .setAddress(List.of(address1))
                .setOrders(List.of(order1))
                .build();

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
        Cart updatedCart = new Cart.Builder().copy(cart).setCartID(2l).build();
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
