package za.co.admatech.service;

import za.co.admatech.domain.Cart;

import java.util.List;

public interface ICartService extends IService<Cart, String>{
    List<Cart> getCarts();
}
