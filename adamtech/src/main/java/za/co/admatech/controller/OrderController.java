package za.co.admatech.controller;

import org.springframework.http.ResponseEntity;
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
    public Order create(@RequestBody Order order) {
        return service.create(order);
    }

    @GetMapping("/read/{orderID}")
    public Order read(@PathVariable Long orderID) {
        return service.read(orderID);
    }

    @PutMapping("/update")
    public Order update(@RequestBody Order order) {
        return service.update(order);
    }

    @DeleteMapping("/delete/{orderID}")
    public boolean delete(@PathVariable Long orderID) {
        return service.delete(orderID);
    }

    // Get all orders as DTOs for frontend
    @GetMapping("/getAll")
    public List<OrderDTO> getAllOrders() {
        return service.getAll()
                .stream()
                .map(OrderDTO::new)
                .collect(Collectors.toList());
    }

    // --- Extra features ---
    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {
        Order updated = service.updateStatus(orderId, OrderStatus.valueOf(status.toUpperCase()));
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/customer/{email}")
    public List<OrderDTO> getOrdersByCustomer(@PathVariable String email) {
        return service.getOrdersByCustomer(email)
                .stream()
                .map(OrderDTO::new)
                .collect(Collectors.toList());
    }
//    @PutMapping("/{orderId}/status")
//    public Order updateOrderStatus(
//            @PathVariable Long orderId,
//            @RequestParam OrderStatus status) {
//        return service.updateOrderStatus(orderId, status);
//    }

}
