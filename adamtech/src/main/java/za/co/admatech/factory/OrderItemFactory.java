package za.co.admatech.factory;


import za.co.admatech.domain.Money;
import za.co.admatech.domain.Order;
import za.co.admatech.domain.OrderItem;
import za.co.admatech.domain.Product;
import za.co.admatech.util.Helper;

public class OrderItemFactory {
    public static OrderItem createOrderItem(String orderItemId, String productId, int quantity, Money unitPrice, Order order, Product product) {
        if (Helper.isNullOrEmpty(orderItemId) || Helper.isNullOrEmpty(productId) || quantity < 0 || unitPrice == null || order == null || product == null) {
            return null;
        }
        return new OrderItem.Builder()
                .orderItemId(orderItemId)
                .productId(productId)
                .quantity(quantity)
                .unitPrice(unitPrice)
                .order(order)
                .product(product)
                .build();
    }

    public static OrderItem createOrderItem(String productId, int quantity, Money unitPrice, Order order, Product product) {
        return createOrderItem(Helper.generateId(), productId, quantity, unitPrice, order, product);
    }
}