package za.co.admatech.domain.order_details;

import java.time.LocalDate;

public class Order {

    private String id;
    private String customerId;
    private LocalDate orderDate;
    private String orderStatus;
    private String totalAmount;

    public Order() {
    }

    public Order(Builder builder) {
        this.id = builder.id;
        this.customerId = builder.customerId;
        this.orderDate = builder.orderDate;
        this.orderStatus = builder.orderStatus;
        this.totalAmount = builder.totalAmount;
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", orderDate=" + orderDate +
                ", orderStatus='" + orderStatus + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                '}';
    }

    public static class Builder {
        private String id;
        private String customerId;
        private LocalDate orderDate;
        private String orderStatus;
        private String totalAmount;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setCustomerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder setOrderDate(LocalDate orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public Builder setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public Builder setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder copy(Order order) {
            this.id = order.id;
            this.customerId = order.customerId;
            this.orderDate = order.orderDate;
            this.orderStatus = order.orderStatus;
            this.totalAmount = order.totalAmount;
            return this;
        }
        public Order build() {
            return new Order(this);
        }

    }
}


