package za.co.admatech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.admatech.domain.Customer;
import za.co.admatech.domain.Order;
import za.co.admatech.domain.enums.OrderStatus;
import za.co.admatech.repository.CustomerRepository;
import za.co.admatech.repository.OrderRepository;

import java.util.List;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public Order create(Order order) {
        // Ensure the customer exists
        String customerEmail = order.getCustomer().getEmail();
        if (customerEmail == null) {
            throw new RuntimeException("Customer email is required for an order");
        }

        Customer existingCustomer = customerRepository.findById(customerEmail)
                .orElseThrow(() -> new RuntimeException("Customer not found with email: " + customerEmail));

        order.setCustomer(existingCustomer); // attach managed entity

        return this.repository.save(order);
    }



    @Override
    public Order read(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public Order update(Order order) {
        return this.repository.save(order);
    }

    @Override
    public boolean delete(Long id) {
        this.repository.deleteById(id);
        return true;
    }

    @Override
    public List<Order> getAll() {
        return this.repository.findAll();
    }

    // --- Extra methods ---

    @Override
    public Order updateStatus(Long orderId, OrderStatus status) {
        return repository.findById(orderId).map(order -> {
            order.setOrderStatus(status);
            return repository.save(order);
        }).orElse(null);
    }

    @Override
    public List<Order> getOrdersByCustomer(String email) {
        return repository.findByCustomerEmail(email);
    }
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = read(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found with ID: " + orderId);
        }
        order.setOrderStatus(status);
        return repository.save(order);
    }

}
