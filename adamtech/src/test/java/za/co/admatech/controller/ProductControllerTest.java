package za.co.admatech.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.boot.test.web.client.TestRestTemplate;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Product;
import za.co.admatech.security.JwtAuthenticationFilter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class ProductControllerTest {

    private static Product product;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private static final String BASE_URL = "/adamtech/products";

    @BeforeAll
    public static void setUp() {
        Money price = new Money.Builder()
                .setAmount(1000)
                .setCurrency("USD")
                .build();

        product = new Product.Builder()
                .setName("Test Product")
                .setDescription("This is a test product")
                .setSku("TP1000")
                .setPrice(price)
                .setCategoryId("CAT1")
                .build();
    }

    @Test
    void a_create() {
        // Mock authentication for ADMIN role
        Authentication auth = new UsernamePasswordAuthenticationToken(
                "admin", null, List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        String url = BASE_URL + "/create";
        ResponseEntity<Product> postResponse = this.restTemplate.postForEntity(url, product, Product.class);
        assertNotNull(postResponse, "Response should not be null");
        assertEquals(200, postResponse.getStatusCodeValue(), "Expected 200 OK");
        Product createdProduct = postResponse.getBody();
        assertNotNull(createdProduct, "Created product should not be null");
        assertNotNull(createdProduct.getProductId(), "Product ID should not be null");
        assertEquals(product.getName(), createdProduct.getName());
        assertEquals(product.getCategoryId(), createdProduct.getCategoryId());

        product = new Product.Builder().copy(createdProduct).build();
        System.out.println("Created: " + createdProduct);
    }

    @Test
    void b_read() {
        // Mock authentication for USER role
        Authentication auth = new UsernamePasswordAuthenticationToken(
                "user", null, List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        String url = BASE_URL + "/read/" + product.getProductId();
        ResponseEntity<Product> response = this.restTemplate.getForEntity(url, Product.class);
        assertEquals(200, response.getStatusCodeValue(), "Expected 200 OK");
        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals(product.getProductId(), response.getBody().getProductId());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    void c_update() {
        // Mock authentication for USER role
        Authentication auth = new UsernamePasswordAuthenticationToken(
                "user", null, List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        Product updatedProduct = new Product.Builder()
                .copy(product)
                .setName("Updated Product")
                .setDescription("Updated description")
                .setSku("TP1000-U")
                .setPrice(new Money.Builder().setAmount(1200).setCurrency("USD").build())
                .setCategoryId("CAT2")
                .build();

        String url = BASE_URL + "/update/" + product.getProductId();
        HttpEntity<Product> request = new HttpEntity<>(updatedProduct);
        ResponseEntity<Product> response = restTemplate.exchange(url, HttpMethod.PUT, request, Product.class);

        assertEquals(200, response.getStatusCodeValue(), "Expected 200 OK");
        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals("Updated Product", response.getBody().getName());
        System.out.println("Updated: " + response.getBody());

        product = response.getBody();
    }

    @Test
    void d_delete() {
        // Mock authentication for ADMIN role
        Authentication auth = new UsernamePasswordAuthenticationToken(
                "admin", null, List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        String url = BASE_URL + "/delete/" + product.getProductId();
        this.restTemplate.delete(url);

        ResponseEntity<Product> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + product.getProductId(), Product.class);
        assertEquals(404, response.getStatusCodeValue(), "Expected 404 Not Found after deletion");
        System.out.println("Deleted: true");
    }

    @Test
    void e_getAll() {
        // Mock authentication for USER role
        Authentication auth = new UsernamePasswordAuthenticationToken(
                "user", null, List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        String url = BASE_URL + "/getAll";
        ResponseEntity<Product[]> response = this.restTemplate.getForEntity(url, Product[].class);
        assertEquals(200, response.getStatusCodeValue(), "Expected 200 OK");
        assertNotNull(response.getBody(), "Response body should not be null");
        System.out.println("Get All:");
        for (Product p : response.getBody()) {
            System.out.println(p);
        }
    }
}