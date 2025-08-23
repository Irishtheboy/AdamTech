package za.co.admatech.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Product;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class ProductControllerTest {

    private static Product product;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "/products";

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
        String url = BASE_URL + "/create";
        ResponseEntity<Product> postResponse = this.restTemplate.postForEntity(url, product, Product.class);
        assertNotNull(postResponse);
        Product createdProduct = postResponse.getBody();
        assertNotNull(createdProduct);
        assertNotNull(createdProduct.getProductId());
        assertEquals(product.getName(), createdProduct.getName());
        assertEquals(product.getCategoryId(), createdProduct.getCategoryId());

        // Save generated ID for later tests
        product = new Product.Builder().copy(createdProduct).build();

        System.out.println("Created: " + createdProduct);
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + product.getProductId();
        ResponseEntity<Product> response = this.restTemplate.getForEntity(url, Product.class);
        assertNotNull(response.getBody());
        assertEquals(product.getProductId(), response.getBody().getProductId());
        System.out.println("Read: " + response.getBody());
    }


    @Test
    void c_update() {
        Product updatedProduct = new Product.Builder()
                .copy(product)
                .setName("Updated Product")
                .setDescription("Updated description")
                .setSku("TP1000-U")
                .setPrice(new Money.Builder().setAmount(1200).setCurrency("USD").build())
                .setCategoryId("CAT2")
                .build();

        // Use PUT and include productId in URL
        String url = BASE_URL + "/update/" + product.getProductId();
        HttpEntity<Product> request = new HttpEntity<>(updatedProduct);

        ResponseEntity<Product> response = restTemplate.exchange(url, HttpMethod.PUT, request, Product.class);

        assertNotNull(response.getBody());
        assertEquals("Updated Product", response.getBody().getName());
        System.out.println("Updated: " + response.getBody());

        // Update local reference for later tests
        product = response.getBody();
    }


    @Test
    void d_delete() {
        String url = BASE_URL + "/delete/" + product.getProductId();
        this.restTemplate.delete(url);

        ResponseEntity<Product> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + product.getProductId(), Product.class);
        assertNull(response.getBody());
        System.out.println("Deleted: true");
    }

    @Test
    void e_getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<Product[]> response = this.restTemplate.getForEntity(url, Product[].class);
        assertNotNull(response.getBody());
        System.out.println("Get All:");
        for (Product p : response.getBody()) {
            System.out.println(p);
        }
    }
}
