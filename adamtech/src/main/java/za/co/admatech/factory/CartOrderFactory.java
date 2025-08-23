package za.co.admatech.factory;


import za.co.admatech.domain.CartOrder;
import za.co.admatech.domain.Order;
import za.co.admatech.domain.enums.OrderStatus;
import za.co.admatech.util.Helper;

public class CartOrderFactory {
    public static CartOrder createCartOrder(String id, OrderStatus orderStatus, Order order) {
        if (Helper.isNullOrEmpty(id) || order == null) {
            return null;
        }
        return new CartOrder.Builder()
                .id(id)
                .orderStatus(orderStatus)
                .order(order)
                .build();
    }

    public static CartOrder createCartOrder(OrderStatus orderStatus, Order order) {
        return createCartOrder(Helper.generateId(), orderStatus, order);
    }
}