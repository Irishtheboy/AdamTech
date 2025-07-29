package za.co.admatech.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Order {
    @Id
    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Embedded
    @Column(nullable = false)
    private Money totalAmount;

    @ManyToOne
    @JoinColumn(name = "customerId", referencedColumnName = "customerId")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    // Public no-arg constructor
    public Order() {}


    private Order(Builder builder) {
        this.id = builder.id;
        this.orderDate = builder.orderDate;
        this.totalAmount = builder.totalAmount;
        this.customer = builder.customer;
        this.orderItems = builder.orderItems;
    }

    public static class Builder {
        private String id;
        private LocalDateTime orderDate;
        private Money totalAmount;
        private Customer customer;
        private List<OrderItem> orderItems;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder orderDate(LocalDateTime orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public Builder totalAmount(Money totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder customer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder orderItems(List<OrderItem> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }

    public Order copy() {
        return new Builder()
                .id(this.id)
                .orderDate(this.orderDate)
                .totalAmount(this.totalAmount)
                .customer(this.customer)
                .orderItems(this.orderItems)
                .build();
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
    public Money getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Money totalAmount) { this.totalAmount = totalAmount; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public List<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }
}