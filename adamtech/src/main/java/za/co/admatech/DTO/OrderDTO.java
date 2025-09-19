package za.co.admatech.DTO;

import za.co.admatech.domain.Order;
import za.co.admatech.domain.OrderItem;
import za.co.admatech.domain.Customer;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDTO {

    private Long id;
    private String customerName;
    private LocalDate orderDate;
    private String status;
    private double totalAmount;
    private int totalItems; // add totalItems
    private List<OrderItemDTO> items;

    public OrderDTO(Order order) {
        if (order != null) {
            this.id = order.getId();
            this.customerName = order.getCustomer() != null ? order.getCustomer().getFirstName() : "Guest";
            this.orderDate = order.getOrderDate();
            this.status = order.getOrderStatus() != null ? order.getOrderStatus().name() : "PENDING";

            List<OrderItem> orderItems = order.getOrderItems();
            if (orderItems != null && !orderItems.isEmpty()) {
                this.items = orderItems.stream().map(OrderItemDTO::new).collect(Collectors.toList());

                // Calculate totalItems
                this.totalItems = orderItems.stream()
                        .mapToInt(OrderItem::getQuantity)
                        .sum();

                // Calculate totalAmount
                this.totalAmount = orderItems.stream()
                        .mapToDouble(item -> item.getProduct().getPrice().getAmount() * item.getQuantity())
                        .sum();
            } else {
                this.items = List.of();
                this.totalItems = 0;
                this.totalAmount = 0.0;
            }
        }
    }

    public Long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public String getStatus() {
        return status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", orderDate=" + orderDate +
                ", status='" + status + '\'' +
                ", totalAmount=" + totalAmount +
                ", totalItems=" + totalItems +
                ", items=" + items +
                '}';
    }
}
