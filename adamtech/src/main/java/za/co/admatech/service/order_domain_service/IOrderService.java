/*
IOrderService.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025 */
package za.co.admatech.service.order_domain_service;

import za.co.admatech.domain.Order;
import za.co.admatech.service.IService;
import java.util.List;

public interface IOrderService extends IService<Order, String> {
    List<Order> getAll();
}