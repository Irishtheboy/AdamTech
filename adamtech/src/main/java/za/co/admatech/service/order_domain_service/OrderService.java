/*





OrderService.java



Author: Naqeebah Khan (219099073)



Date: 24 May 2025 */ package za.co.admatech.service.order_domain_service;

import jakarta.persistence.EntityNotFoundException; import jakarta.transaction.Transactional; import org.springframework.stereotype.Service; import za.co.admatech.domain.Order; import za.co.admatech.repository.OrderRepository; import za.co.admatech.util.Helper;

import java.util.List;

@Service public class OrderService implements IOrderService { private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public Order create(Order order) {
        if (!Helper.isValidOrder(order)) {
            throw new IllegalArgumentException("Invalid order data");
        }
        return orderRepository.save(order);
    }

    @Override
    public Order read(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with ID " + id + " not found"));
    }

    @Override
    @Transactional
    public Order update(Order order) {
        if (!Helper.isValidOrder(order) || order.getId() == null) {
            throw new IllegalArgumentException("Invalid order data or missing ID");
        }
        if (!orderRepository.existsById(order.getId())) {
            throw new EntityNotFoundException("Order with ID " + order.getId() + " not found");
        }
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        if (!orderRepository.existsById(id)) {
            return false;
        }
        orderRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

}