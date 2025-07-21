/*
 * OrderFactory.java
 * Author: Naqeebah Khan (219099073)
 * Date: 17 May 2025
 */
package za.co.admatech.factory;

import za.co.admatech.domain.Customer;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Order;
import za.co.admatech.domain.OrderItem;
import za.co.admatech.domain.enums.OrderStatus;
import za.co.admatech.util.Helper;

import java.time.LocalDate;
import java.util.List;

public class OrderFactory {
    public static Order createOrder(Long id, LocalDate orderDate, OrderStatus orderStatus, Money totalAmount, List<OrderItem> orderItems, Customer customer) {
        if (orderDate == null || !Helper.isValidLocalDate(orderDate) || orderStatus == null || totalAmount == null || customer == null) {
            throw new IllegalArgumentException("Order date, status, total amount, and customer must be valid");
        }
        if (orderItems == null) {
            throw new IllegalArgumentException("Order items list cannot be null");
        }
        
        return new Order.Builder()
                .setId(id)
                .setCustomer(customer)
                .setOrderDate(orderDate)
                .setOrderStatus(orderStatus)
                .setTotalAmount(totalAmount)
                .setOrderItems(orderItems)
                .build();
    }
}