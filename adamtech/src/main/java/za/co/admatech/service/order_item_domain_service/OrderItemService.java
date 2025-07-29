package za.co.admatech.service.order_item_domain_service;/*
OrderItemService.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025 */


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import za.co.admatech.domain.OrderItem;
import za.co.admatech.repository.OrderItemRepository;

import java.util.List;

@Service
public class OrderItemService implements IOrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    @Transactional
    public OrderItem create(OrderItem orderItem) {
        if (orderItem == null || orderItem.getQuantity() < 0) {
            throw new IllegalArgumentException("Invalid order item data");
        }
        return orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem read(String id) {
        try {
            Long longId = Long.valueOf(id);
            return orderItemRepository.findById(longId)
                    .orElseThrow(() -> new EntityNotFoundException("OrderItem with ID " + id + " not found"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid order item ID format: " + id, e);
        }
    }

    @Override
    @Transactional
    public OrderItem update(OrderItem orderItem) {
        if (orderItem.getOrderItemId() == null || orderItem.getQuantity() < 0) {
            throw new IllegalArgumentException("Invalid order item data or missing ID");
        }
        try {
            Long longId = Long.valueOf(orderItem.getOrderItemId());
            if (!orderItemRepository.existsById(longId)) {
                throw new EntityNotFoundException("OrderItem with ID " + orderItem.getOrderItemId() + " not found");
            }
            return orderItemRepository.save(orderItem);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid order item ID format: " + orderItem.getOrderItemId(), e);
        }
    }

    @Override
    @Transactional
    public boolean delete(String id) {
        try {
            Long longId = Long.valueOf(id);
            if (!orderItemRepository.existsById(longId)) {
                return false;
            }
            orderItemRepository.deleteById(longId);
            return true;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid order item ID format: " + id, e);
        }
    }

    @Override
    public List<OrderItem> getAll() {
        return orderItemRepository.findAll();
    }
}