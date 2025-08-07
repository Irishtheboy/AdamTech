package za.co.admatech.factory;


import za.co.admatech.domain.Customer;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Order;
import za.co.admatech.domain.OrderItem;
import za.co.admatech.util.Helper;

import java.time.LocalDateTime;
import java.util.List;

public class OrderFactory {
    public static Order createOrder(String id, LocalDateTime orderDate, Money totalAmount, Customer customer, List<OrderItem> orderItems) {
        if (Helper.isNullOrEmpty(id) || !Helper.isValidLocalDate(orderDate.toLocalDate()) || totalAmount == null || customer == null || orderItems == null) {
            return null;
        }
        return new Order.Builder()
                .id(id)
                .orderDate(orderDate)
                .totalAmount(totalAmount)
                .customer(customer)
                .orderItems(orderItems)
                .build();
    }

    public static Order createOrder(LocalDateTime orderDate, Money totalAmount, Customer customer, List<OrderItem> orderItems) {
        return createOrder(Helper.generateId(), orderDate, totalAmount, customer, orderItems);
    }
}