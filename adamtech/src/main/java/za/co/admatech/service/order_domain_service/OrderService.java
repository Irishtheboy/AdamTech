/*
OrderService.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025 */
package za.co.admatech.service.order_domain_service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import za.co.admatech.domain.Order;
import za.co.admatech.repository.OrderRepository;
import za.co.admatech.util.Helper;

import java.util.List;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public Order create(Order order) {
        if (order == null || !Helper.isValidLocalDate(order.getOrderDate().toLocalDate())) {
            throw new IllegalArgumentException("Invalid order date");
        }
        return orderRepository.save(order);
    }

    @Override
    public Order read(String id) {
        try {
            Long longId = Long.valueOf(id);
            return orderRepository.findById(longId)
                    .orElseThrow(() -> new EntityNotFoundException("Order with ID " + id + " not found"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid order ID format: " + id, e);
        }
    }

    @Override
    @Transactional
    public Order update(Order order) {
        if (order.getId() == null || !Helper.isValidLocalDate(order.getOrderDate().toLocalDate())) {
            throw new IllegalArgumentException("Invalid order data or missing ID");
        }
        try {
            Long longId = Long.valueOf(order.getId());
            if (!orderRepository.existsById(longId)) {
                throw new EntityNotFoundException("Order with ID " + order.getId() + " not found");
            }
            return orderRepository.save(order);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid order ID format: " + order.getId(), e);
        }
    }

    @Override
    @Transactional
    public boolean delete(String id) {
        try {
            Long longId = Long.valueOf(id);
            if (!orderRepository.existsById(longId)) {
                return false;
            }
            orderRepository.deleteById(longId);
            return true;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid order ID format: " + id, e);
        }
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }
}