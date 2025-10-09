package za.co.admatech.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Product;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class ProductControllerTest {

    private static Product product;
    private static byte[] imageBytes;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "/products";

    @BeforeAll
    static void loadImage() throws Exception {
        // Load image from test resources
        imageBytes = Files.readAllBytes(Paths.get("src/test/resources/MSIKeyboard.png"));

        // Initialize product with image
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
                .setImageData(imageBytes)
                .build();
    }

    @Test
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Product> postResponse = restTemplate.postForEntity(url, product, Product.class);
        assertNotNull(postResponse);

        Product createdProduct = postResponse.getBody();
        assertNotNull(createdProduct);
        assertEquals(product.getName(), createdProduct.getName());

        // ✅ Save the created product directly
        product = createdProduct;
        System.out.println("Created: " + createdProduct);
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + product.getProductId();
        ResponseEntity<Product> response = restTemplate.getForEntity(url, Product.class);

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

        String url = BASE_URL + "/update/" + product.getProductId();
        HttpEntity<Product> request = new HttpEntity<>(updatedProduct);

        ResponseEntity<Product> response = restTemplate.exchange(url, HttpMethod.PUT, request, Product.class);

        assertNotNull(response.getBody());
        assertEquals("Updated Product", response.getBody().getName());
        System.out.println("Updated: " + response.getBody());

        product = response.getBody(); // ✅ update reference
    }

    @SuppressWarnings("deprecation")
    @Test
    void d_getImage() {
        // Build URL for image endpoint
        String url = BASE_URL + "/" + product.getProductId() + "/image";

        // Send GET request
        ResponseEntity<byte[]> response = restTemplate.getForEntity(url, byte[].class);

        assertEquals(200, response.getStatusCodeValue(), "HTTP status should be 200 OK");
        assertNotNull(response.getBody(), "Image byte array should not be null");
        assertTrue(response.getBody().length > 0, "Image byte array should not be empty");

        System.out.println("Retrieved image length: " + response.getBody().length + " bytes");
    }

    @Test
    void e_getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<Product[]> response = restTemplate.getForEntity(url, Product[].class);

        assertNotNull(response.getBody());
        System.out.println("Get All Products:");
        for (Product p : response.getBody()) {
            System.out.println(p);
        }
    }

    @Test
    void f_delete() {
        String url = BASE_URL + "/delete/" + product.getProductId();
        restTemplate.delete(url);

        String readUrl = BASE_URL + "/read/" + product.getProductId();
        ResponseEntity<Product> response = restTemplate.getForEntity(readUrl, Product.class);

        assertTrue(response.getStatusCode().is4xxClientError());
        System.out.println("Deleted: true");
    }
}
