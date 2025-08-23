/*
CategoryService.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025 */
package za.co.admatech.service.category_domain_service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import za.co.admatech.domain.Category;
import za.co.admatech.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Category create(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category data is null");
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category read(String id) {
        try {
            Long longId = Long.valueOf(id);
            return categoryRepository.findById(longId)
                    .orElseThrow(() -> new EntityNotFoundException("Category with ID " + id + " not found"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid category ID format: " + id, e);
        }
    }

    @Override
    @Transactional
    public Category update(Category category) {
        if (category.getCategoryId() == null) {
            throw new IllegalArgumentException("Missing Category ID");
        }
        try {
            Long longId = Long.valueOf(category.getCategoryId());
            if (!categoryRepository.existsById(longId)) {
                throw new EntityNotFoundException("Category with ID " + category.getCategoryId() + " not found");
            }
            return categoryRepository.save(category);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid category ID format: " + category.getCategoryId(), e);
        }
    }

    @Override
    @Transactional
    public boolean delete(String id) {
        try {
            Long longId = Long.valueOf(id);
            if (!categoryRepository.existsById(longId)) {
                return false;
            }
            categoryRepository.deleteById(longId);
            return true;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid category ID format: " + id, e);
        }
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}