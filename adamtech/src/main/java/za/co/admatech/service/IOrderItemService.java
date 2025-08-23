package za.co.admatech.service;

import za.co.admatech.domain.OrderItem;

import java.util.List;

public interface IOrderItemService extends IService<OrderItem, Long> {
    List<OrderItem> getOrderItems();
}
