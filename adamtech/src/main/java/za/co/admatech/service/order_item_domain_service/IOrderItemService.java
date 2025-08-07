package za.co.admatech.service.order_item_domain_service;/*
IOrderItemService.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025 */


import za.co.admatech.domain.OrderItem;
import za.co.admatech.service.IService;
import java.util.List;

public interface IOrderItemService extends IService<OrderItem, String> {
    List<OrderItem> getAll();
}