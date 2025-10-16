package za.co.admatech.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.DTO.OrderDTO;
import za.co.admatech.domain.Order;
import za.co.admatech.domain.enums.OrderStatus;
import za.co.admatech.service.OrderService;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public Order create(@RequestBody Order order) {
        return service.create(order);
    }

    @GetMapping("/read/{orderID}")
    @PreAuthorize("isAuthenticated()")
    public Order read(@PathVariable Long orderID) {
        return service.read(orderID);
    }

    @PutMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public Order update(@RequestBody Order order) {
        return service.update(order);
    }

    @DeleteMapping("/delete/{orderID}")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean delete(@PathVariable Long orderID) {
        return service.delete(orderID);
    }

    // Get all orders as DTOs for frontend (Admin only)
    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderDTO> getAllOrders() {
        return service.getAll()
                .stream()
                .map(OrderDTO::new)
                .collect(Collectors.toList());
    }

    // Update order status
    @PutMapping("/{orderId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Order> updateStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {
        try {
            OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
            Order updated = service.updateStatus(orderId, orderStatus);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get orders by customer email
    @GetMapping("/customer/{email}")
    @PreAuthorize("isAuthenticated()")
    public List<OrderDTO> getOrdersByCustomer(@PathVariable String email) {
        return service.getOrdersByCustomer(email)
                .stream()
                .map(OrderDTO::new)
                .collect(Collectors.toList());
    }
}