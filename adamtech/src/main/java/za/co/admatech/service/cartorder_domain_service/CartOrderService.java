/*
CartOrderService.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025 */
package za.co.admatech.service.cartorder_domain_service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import za.co.admatech.domain.CartOrder;
import za.co.admatech.repository.CartOrderRepository;

import java.util.List;

@Service
public class CartOrderService implements ICartOrderService {

    private final CartOrderRepository cartOrderRepository;

    public CartOrderService(CartOrderRepository cartOrderRepository) {
        this.cartOrderRepository = cartOrderRepository;
    }

    @Override
    @Transactional
    public CartOrder create(CartOrder cartOrder) {
        if (cartOrder == null) {
            throw new IllegalArgumentException("CartOrder data is null");
        }
        return cartOrderRepository.save(cartOrder);
    }

    @Override
    public CartOrder read(String id) {
        try {
            Long longId = Long.valueOf(id);
            return cartOrderRepository.findById(longId)
                    .orElseThrow(() -> new EntityNotFoundException("CartOrder with ID " + id + " not found"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid cart order ID format: " + id, e);
        }
    }

    @Override
    @Transactional
    public CartOrder update(CartOrder cartOrder) {
        if (cartOrder.getId() == null) {
            throw new IllegalArgumentException("Missing CartOrder ID");
        }
        try {
            Long longId = Long.valueOf(cartOrder.getId());
            if (!cartOrderRepository.existsById(longId)) {
                throw new EntityNotFoundException("CartOrder with ID " + cartOrder.getId() + " not found");
            }
            return cartOrderRepository.save(cartOrder);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid cart order ID format: " + cartOrder.getId(), e);
        }
    }

    @Override
    @Transactional
    public boolean delete(String id) {
        try {
            Long longId = Long.valueOf(id);
            if (!cartOrderRepository.existsById(longId)) {
                return false;
            }
            cartOrderRepository.deleteById(longId);
            return true;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid cart order ID format: " + id, e);
        }
    }

    @Override
    public List<CartOrder> getAll() {
        return cartOrderRepository.findAll();
    }
}