package za.co.admatech.service.cart_item_domain_service;/*
ICartItemService.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025 */


import za.co.admatech.domain.CartItem;
import za.co.admatech.service.IService;
import java.util.List;

public interface ICartItemService extends IService<CartItem, String> {
    List<CartItem> getAll();
}