/*OrderController.java
  Order controller Class
  Author: Naqeebah Khan (219099073)
  Date: 03 June 2025
 */

package za.co.admatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/getAll")
    public List<Order> getAll() {
        return service.getAll();
    }

}
