/*





ProductService.java



Author: Seymour Lawrence (230185991)



Date: 25 May 2025 */ package za.co.admatech.service.product_domain_service;

import jakarta.persistence.EntityNotFoundException; import jakarta.transaction.Transactional; import org.springframework.stereotype.Service; import za.co.admatech.domain.Product; import za.co.admatech.repository.ProductRepository; import za.co.admatech.util.Helper;

import java.util.List;

@Service public class ProductService implements IProductService { private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Product create(Product product) {
        if (!Helper.isValidProduct(product)) {
            throw new IllegalArgumentException("Invalid product data");
        }
        return productRepository.save(product);
    }

    @Override
    public Product read(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + id + " not found"));
    }

    @Override
    @Transactional
    public Product update(Product product) {
        if (!Helper.isValidProduct(product) || product.getProductId() == null) {
            throw new IllegalArgumentException("Invalid product data or missing ID");
        }
        if (!productRepository.existsById(product.getProductId()) {
            throw new EntityNotFoundException("Product with ID " + product.getProductId() + " not found");
        }
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        if (!productRepository.existsById(id)) {
            return false;
        }
        productRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

}