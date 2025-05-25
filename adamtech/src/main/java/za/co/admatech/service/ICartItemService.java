package za.co.admatech.service;

import za.co.admatech.domain.CartItem;

import java.util.List;

public interface ICartItemService extends IService<CartItem, String>{
    List<CartItem> getAll();
}
