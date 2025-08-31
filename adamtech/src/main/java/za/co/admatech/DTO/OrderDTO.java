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
    private List<OrderItemDTO> items;


    public OrderDTO(Order order) {
        if (order != null) {
            this.id = order.getId();


            Customer customer = order.getCustomer();
            this.customerName = customer != null ? customer.getFirstName() : "Guest";

            this.orderDate = order.getOrderDate();
            OrderStatus orderStatus = order.getOrderStatus();
            this.status = orderStatus != null ? orderStatus.name() : "PENDING";

            Money total = order.getTotalAmount();
            this.totalAmount = total != null ? total.getAmount() : 0.0;


            List<OrderItem> orderItems = order.getOrderItems();
            if (orderItems != null) {
                this.items = orderItems.stream()
                        .map(OrderItemDTO::new)
                        .collect(Collectors.toList());
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
                ", items=" + items +
                '}';
    }
}
