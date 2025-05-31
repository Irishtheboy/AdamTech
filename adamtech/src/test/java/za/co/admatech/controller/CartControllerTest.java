/*CartControllerTest.java
  Author: Teyana Raubenheimer (230237622)
  Date: 31 May 2025
 */

package za.co.admatech.controller;

import org.apache.catalina.connector.Response;
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
import za.co.admatech.factory.CartFactory;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.class)
class CartControllerTest {

    private static Cart cart;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:8080/adamtech/cart/";

    @BeforeAll
    public static void setUp() {
        cart = CartFactory.createCart("1010", "5");
    }

    @Test
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Cart> postResponse = this.restTemplate.postForEntity(url, cart, Cart.class);
        assertNotNull(postResponse);
        Cart createdCart = postResponse.getBody();
        assertEquals(cart.getCartID(), createdCart.getCartID());
        System.out.println("Created: " + createdCart);

    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + cart.getCartID();
        ResponseEntity<Cart> response = this.restTemplate.getForEntity(url, Cart.class);
       // assertNotNull(response);
        assertEquals(cart.getCartID(), response.getBody().getCartID());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    void c_update() {
        Cart updatedCart = new Cart.Builder().copy(cart).setCartID("2020").build();
        String url = BASE_URL + "/update";
        ResponseEntity<Cart> response = this.restTemplate.postForEntity(url, updatedCart, Cart.class);
        //assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertEquals(updatedCart.getCartID(), response.getBody().getCartID());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    void d_delete() {
        String url = BASE_URL + "/delete/" + cart.getCartID();
        this.restTemplate.delete(url);
        ResponseEntity<Cart> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + cart.getCartID(), Cart.class);;
        assertNotNull(response.getBody());
        System.out.println("Deleted: " + "true");
    }

    @Test
    void e_getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<Cart[]> response = this.restTemplate.getForEntity(url, Cart[].class);
        assertNotNull(response.getBody());
        //assertTrue(response.getBody().length > 0);
        System.out.println("Get All: " );
        for (Cart c : response.getBody()) {
            System.out.println(c);
        }
    }


}
