package za.co.admatech.controller;

import org.springframework.beans.factory.annotation.Autowired;

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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.domain.Product;
import za.co.admatech.dto.ApiResponse;
import za.co.admatech.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // GET all products with pagination
    @GetMapping
    public ResponseEntity<?> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String search) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> products;
        
        if (search != null && !search.isEmpty()) {
            // If search query provided, filter products
            products = productRepository.findAll(pageable); // You can implement search in repository
        } else {
            products = productRepository.findAll(pageable);
        }
        
        return ResponseEntity.ok(ApiResponse.success("Products retrieved successfully", 
            products.getContent(),
            ApiResponse.createPaginationMetadata(page, size, products.getTotalElements(), products.getTotalPages())));
    }

    // GET product by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(p -> ResponseEntity.ok(ApiResponse.success("Product found", p)))
                .orElse(ResponseEntity.notFound().build());
    }

    // POST create new product (Admin only)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        try {
            Product savedProduct = productRepository.save(product);
            return ResponseEntity.ok(ApiResponse.success("Product created successfully", savedProduct));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to create product: " + e.getMessage()));
        }
    }

    // PUT update existing product (Admin only)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody Product updatedProduct) {
        return productRepository.findById(id).map(product -> {
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setSku(updatedProduct.getSku());
            product.setPrice(updatedProduct.getPrice());
            product.setCategory(updatedProduct.getCategory());
            product.setProductType(updatedProduct.getProductType());
            Product saved = productRepository.save(product);
            return ResponseEntity.ok(ApiResponse.success("Product updated successfully", saved));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE product (Admin only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productRepository.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success("Product deleted successfully"));

    }
}
