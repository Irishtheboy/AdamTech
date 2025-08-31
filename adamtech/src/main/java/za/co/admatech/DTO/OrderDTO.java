package za.co.admatech.DTO;

import za.co.admatech.domain.Order;
import za.co.admatech.domain.OrderItem;
import za.co.admatech.domain.Customer;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

// DTO for sending complete order info to frontend
public class OrderDTO {

    private Long id;                  // Order ID
    private String customerName;       // Customer name
    private LocalDate orderDate;       // Order date
    private String status;             // Order status (as string)
    private double totalAmount;        // Total amount of order
    private List<OrderItemDTO> items;  // List of order items

    // Constructor mapping Order entity to DTO
    public OrderDTO(Order order) {
        if (order != null) {
            this.id = order.getId();

            // Extract customer name safely
            Customer customer = order.getCustomer();
            this.customerName = customer != null ? customer.getFirstName() : "Guest";

            this.orderDate = order.getOrderDate();
            OrderStatus orderStatus = order.getOrderStatus();
            this.status = orderStatus != null ? orderStatus.name() : "PENDING";

            Money total = order.getTotalAmount();
            this.totalAmount = total != null ? total.getAmount() : 0.0;

            // Map OrderItems to OrderItemDTOs
            List<OrderItem> orderItems = order.getOrderItems();
            if (orderItems != null) {
                this.items = orderItems.stream()
                        .map(OrderItemDTO::new)
                        .collect(Collectors.toList());
            }
        }
    }

    // Getters
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
