/*CartItemControllerTest.java
  Author: Teyana Raubenheimer (230237622)
  Date: 31 May 2025
 */

package za.co.admatech.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.co.admatech.domain.Cart;
import za.co.admatech.domain.CartItem;
import za.co.admatech.factory.CartFactory;
import za.co.admatech.factory.CartItemFactory;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.class)
class CartItemControllerTest {

    private static CartItem cartItem;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:8080/adamtech/cart-items/";

    @BeforeAll
    public static void setUp() {
        cartItem = CartItemFactory.createCartItem("1111", 20, "201");
    }

    @Test
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<CartItem> postResponse = this.restTemplate.postForEntity(url, cartItem, CartItem.class);
        assertNotNull(postResponse);
        CartItem createdCartItem = postResponse.getBody();
        assertEquals(cartItem.getCartID(), createdCartItem.getCartID());
        System.out.println("Created: " + createdCartItem);

    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + cartItem.getCartItemID();
        ResponseEntity<CartItem> response = this.restTemplate.getForEntity(url, CartItem.class);
        // assertNotNull(response);
        assertEquals(cartItem.getCartID(), response.getBody().getCartItemID());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    void c_update() {
        CartItem updatedCartItem = new CartItem.Builder().copy(cartItem).setCartItemID("2222").build();
        String url = BASE_URL + "/update";
        ResponseEntity<CartItem> response = this.restTemplate.postForEntity(url, updatedCartItem, CartItem.class);
        //assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertEquals(updatedCartItem.getCartItemID(), response.getBody().getCartItemID());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    void d_delete() {
        String url = BASE_URL + "/delete/" + cartItem.getCartID();
        this.restTemplate.delete(url);
        ResponseEntity<CartItem> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + cartItem.getCartItemID(), CartItem.class);;
        assertNotNull(response.getBody());
        System.out.println("Deleted: " + "true");
    }

    @Test
    void e_getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<CartItem[]> response = this.restTemplate.getForEntity(url, CartItem[].class);
        assertNotNull(response.getBody());
        //assertTrue(response.getBody().length > 0);
        System.out.println("Get All: " );
        for (CartItem ci : response.getBody()) {
            System.out.println(ci);
        }
    }

}