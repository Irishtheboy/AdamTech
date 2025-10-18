package za.co.admatech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.admatech.domain.Product;
import za.co.admatech.repository.ProductRepository;
import java.util.List;

@Service
public class ProductService implements IProductService {

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

    @Override
    public List<Product> searchByName(String q) {
        if (q == null || q.trim().isEmpty()) return repository.findAll();
        String pattern = "%" + q.trim().toLowerCase() + "%";
        // using a repository method (see below) or filter in Java if repository lacks method
        return repository.findByNameContainingIgnoreCase(q);
    }
}
