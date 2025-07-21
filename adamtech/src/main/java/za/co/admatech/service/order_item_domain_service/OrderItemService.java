/*





OrderItemService.java



Author: Naqeebah Khan (219099073)



Date: 24 May 2025 */ package za.co.admatech.service.order_item_domain_service;

import jakarta.persistence.EntityNotFoundException; import jakarta.transaction.Transactional; import org.springframework.stereotype.Service; import za.co.admatech.domain.OrderItem; import za.co.admatech.repository.OrderItemRepository;

import java.util.List;

@Service public class OrderItemService implements IOrderItemService { private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    @Transactional
    public OrderItem create(OrderItem orderItem) {
        if (orderItem == null || orderItem.getProduct() == null || orderItem.getOrder() == null || orderItem.getQuantity() <= 0 || orderItem.getUnitPrice() == null) {
            throw new IllegalArgumentException("Invalid order item data");
        }
        return orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem read(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderItem with ID " + id + " not found"));
    }

    @Override
    @Transactional
    public OrderItem update(OrderItem orderItem) {
        if (orderItem == null || orderItem.getId() == null || orderItem.getProduct() == null || orderItem.getOrder() == null || orderItem.getQuantity() <= 0 || orderItem.getUnitPrice() == null) {
            throw new IllegalArgumentException("Invalid order item data or missing ID");
        }
        if (!orderItemRepository.existsById(orderItem.getId())) {
            throw new EntityNotFoundException("OrderItem with ID " + orderItem.getId() + " not found");
        }
        return orderItemRepository.save(orderItem);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        if (!orderItemRepository.existsById(id)) {
            return false;
        }
        orderItemRepository.deleteById(id);
        return true;
    }

    @Override
    public List<OrderItem> getAll() {
        return orderItemRepository.findAll();
    }

}