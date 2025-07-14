/*
 * ProductService.java
 * ProductService Class
 * Author: Seymour Lawrence (230185991)
 * Date: 25 May 2025
 */
package za.co.admatech.service.product_domain_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.admatech.domain.Product;
import za.co.admatech.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService implements IProductService {

    private static IProductService service;

    @Autowired
    private ProductRepository repository;

    @Override
    public Product create(Product product) {
        return this.repository.save(product);
    }

    @Override
    public Product read(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public Product update(Product product) {
        return this.repository.save(product);
    }

    @Override
    public boolean delete(Long id) {
        this.repository.deleteById(id);
        return true;
    }

    @Override
    public List<Product> getAll() {
        return this.repository.findAll();
    }
}