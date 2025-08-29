package za.co.admatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.domain.Product;
import za.co.admatech.service.ProductService;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    // Create a new product
    @PostMapping("/create")
    public ResponseEntity<Product> create(@RequestBody Product product) {
        return ResponseEntity.ok(service.create(product));
    }

    // Read a product by ID
    @GetMapping("/read/{productId}")
    public ResponseEntity<Product> read(@PathVariable Long productId) {
        Product product = service.read(productId);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    // Update a product
    @PutMapping("/update/{productId}")
    public ResponseEntity<Product> update(@PathVariable Long productId, @RequestBody Product product) {
        // Create a copy with the ID from path
        Product productWithId = new Product.Builder()
                .copy(product)
                .setProductId(productId)
                .build();

        Product updated = service.update(productWithId);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }



    // Delete a product
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> delete(@PathVariable Long productId) {
        boolean deleted = service.delete(productId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Get all products
    @GetMapping("/getAll")
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }


    @GetMapping("/{productId}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long productId) {
        Product product = service.read(productId);
        if (product == null || product.getImageData() == null) {
            return ResponseEntity.notFound().build();
        }

        // Set the content type for the image
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE) // or IMAGE_JPEG_VALUE
                .body(product.getImageData());
    }


}
