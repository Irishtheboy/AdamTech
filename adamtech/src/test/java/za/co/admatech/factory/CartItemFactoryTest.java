/*Cart.java
  Cart Class
  Author: Teyana Raubenheimer (230237622)
  Date: 18 May 2025
 */

package za.co.admatech.factory;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Cart;
import za.co.admatech.domain.CartItem;
import za.co.admatech.domain.Customer;
import za.co.admatech.domain.Product;
import za.co.admatech.domain.enums.ProductCategory;
import za.co.admatech.domain.enums.ProductType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartItemFactoryTest {

    private static CartItem cartItem;
    private static Cart cart;
    private static Product product;
    private static Customer customer;

    @BeforeAll
    public static void setUp() {
        product = ProductFactory.createProduct(
                124L,
                "Test Product",
                "Description",
                new za.co.admatech.domain.Money(1000, "ZAR"),
                ProductCategory.GAMING,
                ProductType.PERIPHERAL
        );
        customer = CustomerFactory.createCustomer(
                125L,
                "John",
                "Doe",
                "john.doe@example.com",
                "1234567890",
                null, // Will be set after cart creation
                List.of(AddressFactory.createAddress(
                        126L,
                        (short) 12,
                        "Main Street",
                        "Suburb",
                        "City",
                        "Province",
                        (short) 1234
                )),
                List.of()
        );
        cart = CartFactory.createCart(
                127L,
                customer,
                List.of()
        );
        cartItem = CartItemFactory.createCartItem(
                128L,
                product,
                26,
                cart
        );
    }

    @Test
    @Order(1)
    public void testCreateCartItem() {
        assertNotNull(cartItem);
        System.out.println(cartItem.toString());

    }

    @Test
    @Order(2)
    public void testCreateCartItemThatFails() {
        //fail();
        assertNotNull(cartItem);
        System.out.println(cartItem.toString());
    }
}