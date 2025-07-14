package za.co.admatech.service.order_item_domain_service;

import za.co.admatech.domain.OrderItem;
import za.co.admatech.service.IService;

import java.util.List;

public interface IOrderItemService extends IService<OrderItem, String> {
    List<OrderItem> getOrderItems();
}
