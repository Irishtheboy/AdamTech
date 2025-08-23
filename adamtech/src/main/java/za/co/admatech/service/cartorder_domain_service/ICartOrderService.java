/*
ICartOrderService.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025 */
package za.co.admatech.service.cartorder_domain_service;

import za.co.admatech.domain.CartOrder;
import za.co.admatech.service.IService;
import java.util.List;

public interface ICartOrderService extends IService<CartOrder, String> {
    List<CartOrder> getAll();
}