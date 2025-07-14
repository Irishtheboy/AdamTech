/*OrderService.java
  Order Service Class
  Author: Naqeebah Khan (219099073)
  Date: 24 May 2025
 */

package za.co.admatech.service.order_domain_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.admatech.domain.Order;
import za.co.admatech.repository.OrderRepository;
import za.co.admatech.service.IService;

import java.util.List;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;
    public OrderService(OrderRepository repository) {
        this.orderRepository = repository;
    }

    @Override
    public Order create(Order order) {
        return this.orderRepository.save(order);
    }

    @Override
    public Order read(Long id) {
        return this.orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order update(Order order) {
        return this.orderRepository.save(order);
    }

    @Override
    public boolean delete(Long id) {
        this.orderRepository.deleteById(id);
        return true;
    }


    public List<Order> getAll(){
        return this.orderRepository.findAll();
    }

}
