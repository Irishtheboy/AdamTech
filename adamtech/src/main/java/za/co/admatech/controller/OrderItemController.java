/*OrderItemController.java
  Order Item Controller Class
  Author: Naqeebah Khan (219099073)
  Date: 03 June 2025
 */
package za.co.admatech.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.domain.OrderItem;
import za.co.admatech.service.OrderItemService;

import java.util.List;

@RestController
@RequestMapping("/orderItem")
public class OrderItemController {

    private OrderItemService service;

    @Autowired
    public OrderItemController(OrderItemService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public OrderItem create(@RequestBody OrderItem orderItem) {
        return service.create(orderItem);
    }

    @GetMapping("/read/{orderItemID}")
    public OrderItem read(@PathVariable String orderItemID) {
        return service.read(orderItemID);
    }

    @PutMapping("/update")
    public OrderItem update(@RequestBody OrderItem orderItem) {
        return service.update(orderItem);
    }

    @DeleteMapping("/delete/{orderItemID}")
    public boolean delete(@PathVariable String orderItemID) {
        return service.delete(orderItemID);
    }

    @GetMapping("/getAll")
    public List<OrderItem> getAll() {
        return service.getAll();
    }
}
