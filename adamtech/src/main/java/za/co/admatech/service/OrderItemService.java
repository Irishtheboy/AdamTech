/*
 * OrderItemService.java
 * Order Item Service Class
 * Author: Naqeebah Khan (219099073)
 * Date: 24 May 2025
 */

package za.co.admatech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.admatech.domain.OrderItem;
import za.co.admatech.repository.OrderItemRepository;

import java.util.List;

@Service
public class OrderItemService implements IOrderItemService {

    @Autowired
    private OrderItemRepository repository;

    @Override
    public OrderItem create(OrderItem orderItem) {
        return repository.saveAndFlush(orderItem); // save and flush in one call
    }

    @Override
    public OrderItem read(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public OrderItem update(OrderItem orderItem) {
        return repository.save(orderItem);
    }

    @Override
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false; // safer: avoid returning true if item didn’t exist
    }

    @Override
    public List<OrderItem> getAll() {
        return repository.findAll();
    }
}
