/*OrderController.java
  Order controller Class
  Author: Naqeebah Khan (219099073)
  Date: 03 June 2025
 */

package za.co.admatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.domain.Order;
import za.co.admatech.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderService service;

    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping ("/create")
    public ResponseEntity<Order> create(@RequestBody Order order) {
        return ResponseEntity.ok(service.create(order));
    }

    @GetMapping("/read/{orderID}")
    public ResponseEntity<Order> read(@PathVariable Long orderID) {
        Order order = service.read(orderID);
        return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{orderID}")
    public ResponseEntity<Order> update(@PathVariable Long orderId, @RequestBody Order order) {
        Order orderWithId = new Order.Builder()
                .copy(order)
                .setOrderId(orderId)
                .build();

        Order updatedOrder = service.update(orderWithId);
        return updatedOrder != null ? ResponseEntity.ok(updatedOrder) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<Void> delete(@PathVariable Long orderId) {
        boolean deleted = service.delete(orderId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Order>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

}
