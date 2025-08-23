/*
CartService.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025 */
package za.co.admatech.service.cart_domain_service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import za.co.admatech.domain.Cart;
import za.co.admatech.repository.CartRepository;

import java.util.List;

@Service
public class CartService implements ICartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    @Transactional
    public Cart create(Cart cart) {
        if (cart == null) {
            throw new IllegalArgumentException("Cart data is null");
        }
        return cartRepository.save(cart);
    }

    @Override
    public Cart read(String id) {
        try {
            Long longId = Long.valueOf(id);
            return cartRepository.findById(longId)
                    .orElseThrow(() -> new EntityNotFoundException("Cart with ID " + id + " not found"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid cart ID format: " + id, e);
        }
    }

    @Override
    @Transactional
    public Cart update(Cart cart) {
        if (cart.getId() == null) {
            throw new IllegalArgumentException("Missing cart ID");
        }
        try {
            Long longId = Long.valueOf(cart.getId());
            if (!cartRepository.existsById(longId)) {
                throw new EntityNotFoundException("Cart with ID " + cart.getId() + " not found");
            }
            return cartRepository.save(cart);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid cart ID format: " + cart.getId(), e);
        }
    }

    @Override
    @Transactional
    public boolean delete(String id) {
        try {
            Long longId = Long.valueOf(id);
            if (!cartRepository.existsById(longId)) {
                return false;
            }
            cartRepository.deleteById(longId);
            return true;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid cart ID format: " + id, e);
        }
    }

    @Override
    public List<Cart> getAll() {
        return cartRepository.findAll();
    }
}