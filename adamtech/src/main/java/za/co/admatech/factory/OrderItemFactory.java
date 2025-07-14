/*OrderItemFactory.java
  OrderItemFactory Class
  Author: Naqeebah Khan (219099073)
  Date: 17 May 2025
 */

package za.co.admatech.factory;

import za.co.admatech.domain.Money;
import za.co.admatech.domain.Order;
import za.co.admatech.domain.OrderItem;
import za.co.admatech.domain.Product;
import za.co.admatech.util.Helper;

public class OrderItemFactory {
    public static OrderItem createOrderItem(Long id,
                                            int quantity,
                                            Money unitPrice,
                                            Order order,
                                            Product product) {

        if (quantity <= 0){
            return null;
        }

        if(unitPrice == null){
            return null;
        }

        return new OrderItem.Builder()
                .setId(id)
                .setQuantity(quantity)
                .setUnitPrice(unitPrice)
                .setOrder(order)
                .setProduct(product)
                .build();
    }
}
