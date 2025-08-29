package za.co.admatech.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import za.co.admatech.domain.Customer;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Product;
import za.co.admatech.domain.Wishlist;
import za.co.admatech.repository.CustomerRepository;
import za.co.admatech.repository.ProductRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WishlistControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    private static Customer customer;
    private static Product product;
    private static Wishlist wishlist;

    private final String baseUrl = "/wishlist";

    @Test
    @Order(0)
    void setupEntities() {
        // Persist Customer
        customer = new Customer.Builder()
                .setFirstName("John")
                .setLastName("Doe")
                .setEmail("john.doe@example.com")
                .setPhoneNumber("0812345678")
                .build();
        customer = customerRepository.save(customer);

        // Persist Product
        product = new Product.Builder()
                .setName("Gaming Laptop")
                .setDescription("High-end gaming laptop")
                .setPrice(new Money(150, "RSA"))
                .setSku("GL12345")
                .setCategoryId("IQQ1")
                .build();
        product = productRepository.save(product);

        assertNotNull(customer.getCustomerId());
        assertNotNull(product.getProductId());
        System.out.println("Setup entities: Customer ID=" + customer.getCustomerId() +
                ", Product ID=" + product.getProductId());
    }

    @Test
    @Order(1)
    void a_createWishlist() {
        wishlist = new Wishlist.Builder()
                .customer(customer)
                .product(product)
                .createdAt(LocalDateTime.now())
                .build();

        ResponseEntity<Wishlist> response = restTemplate.postForEntity(
                baseUrl + "/create", wishlist, Wishlist.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getWishlistId());

        wishlist = response.getBody();
        System.out.println("Created Wishlist ID=" + wishlist.getWishlistId());
    }

    @Test
    @Order(2)
    void b_readWishlist() {
        ResponseEntity<Wishlist> response = restTemplate.getForEntity(
                baseUrl + "/read/" + wishlist.getWishlistId(), Wishlist.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(wishlist.getWishlistId(), response.getBody().getWishlistId());
    }

    @Test
    @Order(3)
    void c_updateWishlist() {
        Product newProduct = new Product.Builder()
                .setName("Mechanical Keyboard")
                .setDescription("RGB backlit mechanical keyboard")
                .setPrice(new Money(50, "RSA"))
                .setSku("KB98765")
                .setCategoryId("IQQ2")
                .build();
        newProduct = productRepository.save(newProduct);

        Wishlist updatedWishlist = new Wishlist.Builder()
                .copy(wishlist)
                .product(newProduct)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Wishlist> request = new HttpEntity<>(updatedWishlist, headers);

        ResponseEntity<Wishlist> response = restTemplate.exchange(
                baseUrl + "/update", HttpMethod.PUT, request, Wishlist.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(newProduct.getName(), response.getBody().getProduct().getName());

        wishlist = response.getBody();
    }

    @Test
    @Order(4)
    void d_getAllWishlists() {
        ResponseEntity<Wishlist[]> response = restTemplate.getForEntity(
                baseUrl + "/getAll", Wishlist[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length >= 1);
    }

    @Test
    @Order(5)
    void e_deleteWishlist() {
        restTemplate.delete(baseUrl + "/delete/" + wishlist.getWishlistId());

        ResponseEntity<Wishlist> response = restTemplate.getForEntity(
                baseUrl + "/read/" + wishlist.getWishlistId(), Wishlist.class);

        assertNull(response.getBody());
    }
}
