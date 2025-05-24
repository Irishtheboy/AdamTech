/*OrderItemService.java
  Order Item Service Class
  Author: Naqeebah Khan (219099073)
  Date: 24 May 2025
 */

package za.co.admatech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.admatech.domain.OrderItem;
import za.co.admatech.repository.OrderItemRepository;

import java.util.List;

@Service
public class OrderItemService implements IService <OrderItem, String>{

    @Autowired
    private OrderItemRepository repository;

    @Override
    public OrderItem create(OrderItem orderItem) {
        return this.repository.save(orderItem);
    }

    @Override
    public OrderItem read(String id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public OrderItem update(OrderItem orderItem) {
        return this.repository.save(orderItem);
    }

    @Override
    public boolean delete(String id){
        this.repository.deleteById(id);
        return true;
    }

    public List<OrderItem> getOrderItems(){
        return this.repository.findAll();
    }
}
