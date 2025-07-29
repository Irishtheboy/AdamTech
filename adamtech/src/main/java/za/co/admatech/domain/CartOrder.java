package za.co.admatech.domain;

import jakarta.persistence.*;
import za.co.admatech.domain.enums.OrderStatus;

@Entity
public class CartOrder {
    @Id
    @Column(nullable = false)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "orderId", referencedColumnName = "id")
    private Order order;

    // Public no-arg constructor
    public CartOrder() {}



    private CartOrder(Builder builder) {
        this.id = builder.id;
        this.orderStatus = builder.orderStatus;
        this.order = builder.order;
    }

    public static class Builder {
        private String id;
        private OrderStatus orderStatus;
        private Order order;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder orderStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public Builder order(Order order) {
            this.order = order;
            return this;
        }

        public CartOrder build() {
            return new CartOrder(this);
        }
    }

    public CartOrder copy() {
        return new Builder()
                .id(this.id)
                .orderStatus(this.orderStatus)
                .order(this.order)
                .build();
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public OrderStatus getOrderStatus() { return orderStatus; }
    public void setOrderStatus(OrderStatus orderStatus) { this.orderStatus = orderStatus; }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
}