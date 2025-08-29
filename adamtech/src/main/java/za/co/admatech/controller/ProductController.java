package za.co.admatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.domain.Product;
import za.co.admatech.service.ProductService;

import java.util.List;

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
    public ResponseEntity<?> create(@RequestBody Product product) {
        try {
            Product created = service.create(product);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create product: " + e.getMessage());
        }
    }

    // Read a product by ID
    @GetMapping("/read/{productId}")
    public ResponseEntity<?> read(@PathVariable Long productId) {
        try {
            Product product = service.read(productId);
            return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error reading product: " + e.getMessage());
        }
    }

    // Update a product
    @PutMapping("/update/{productId}")
    public ResponseEntity<?> update(@PathVariable Long productId, @RequestBody Product product) {
        try {
            Product productWithId = new Product.Builder()
                    .copy(product)
                    .setProductId(productId)
                    .build();

            Product updated = service.update(productWithId);
            return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Update failed: " + e.getMessage());
        }
    }

    // Delete a product
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> delete(@PathVariable Long productId) {
        try {
            boolean deleted = service.delete(productId);
            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Deletion error: " + e.getMessage());
        }
    }

    // Get all products
    @GetMapping("/getAll")
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
