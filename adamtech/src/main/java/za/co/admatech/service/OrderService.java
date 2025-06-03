/*OrderService.java
  Order Service Class
  Author: Naqeebah Khan (219099073)
  Date: 24 May 2025
 */

package za.co.admatech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.admatech.domain.Order;
import za.co.admatech.repository.OrderRepository;

import java.util.List;

@Service
public class OrderService implements IService <Order, String> {


    @Autowired
    private OrderRepository repository;

    @Override
    public Order create(Order order) {
        return this.repository.save(order);
    }

    @Override
    public Order read(String id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public Order update(Order order) {
        return this.repository.save(order);
    }

    @Override
    public boolean delete(String id) {
        this.repository.deleteById(id);
        return true;
    }


    public List<Order> getAll(){
        return this.repository.findAll();
    }

}
