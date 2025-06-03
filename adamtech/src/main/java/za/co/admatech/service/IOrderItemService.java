package za.co.admatech.service;

import za.co.admatech.domain.OrderItem;

import java.util.List;

public interface IOrderItemService extends IService<OrderItem, String> {
    List<OrderItem> getOrderItems();
}
