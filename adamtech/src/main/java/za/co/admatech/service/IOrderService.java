/*IOrderService.java
  Order Service Interface
  Author: Naqeebah Khan (219099073)
  Date: 24 May 2025
 */

package za.co.admatech.service;

import za.co.admatech.domain.Order;
import za.co.admatech.domain.enums.OrderStatus;

import java.util.List;

public interface IOrderService extends IService <Order, Long>{
    List<Order> getAll();

    // Extra methods
    Order updateStatus(Long orderId, OrderStatus status);

    List<Order> getOrdersByCustomer(String email);
}

