/*OrderFactory.java
  OrderFactory Class
  Author: Naqeebah Khan (219099073)
  Date: 17 May 2025
 */
package za.co.admatech.factory;

import za.co.admatech.domain.Customer;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Order;
import za.co.admatech.domain.enums.OrderStatus;
import za.co.admatech.util.Helper;

import java.time.LocalDate;

public class OrderFactory {

    public static Order createOrder(Customer customer, LocalDate orderDate, OrderStatus orderStatus, Money totalAmount) {
        /*
        String id = customerId;

        if(Helper.isNullOrEmpty(customerId)){
            return null;
        }
         */
        if (orderDate == null || !Helper.isValidLocalDate(orderDate)) {
            return null;
        }

        if(orderStatus == null || !Helper.isValidOrderStatus(orderStatus.getStatus())) {
            return null;
        }

        if(totalAmount == null) {
            return null;
        }


        return new Order.Builder()
                .setId(Helper.generateId())
                .setCustomer(customer)
                .setOrderDate(orderDate)
                .setOrderStatus(orderStatus)
                .setTotalAmount(totalAmount)
                .build();
    }
}
