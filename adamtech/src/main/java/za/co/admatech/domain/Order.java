package za.co.admatech.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import za.co.admatech.domain.enums.OrderStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "email")
    private Customer customer;

    private LocalDate orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Embedded
    private Money totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order() {
    }

    protected Order(Builder builder) {
        this.id = builder.id;
        this.customer = builder.customer;
        this.orderDate = builder.orderDate;
        this.orderStatus = builder.orderStatus;
        this.totalAmount = builder.totalAmount;
        this.orderItems = builder.orderItems != null ? builder.orderItems : new ArrayList<>();
        // Link each OrderItem back to this Order
        this.orderItems.forEach(item -> item.setOrder(this));
    }

    // ======== Getters =========
    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Money getTotalAmount() {
        return totalAmount;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    // ======== Setters / State changes =========
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setTotalAmount(Money totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
        // Keep bidirectional link consistent
        if (orderItems != null) {
            orderItems.forEach(item -> item.setOrder(this));
        }
    }

    // ======== Domain-specific helpers =========
    public void markAsPaid() {
        this.orderStatus = OrderStatus.CONFIRMED;
    }

    public void cancel() {
        this.orderStatus = OrderStatus.CANCELLED;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer +
                ", orderDate=" + orderDate +
                ", orderStatus=" + orderStatus +
                ", totalAmount=" + totalAmount +
                ", orderItems=" + orderItems +
                '}';
    }

    // ======== Builder =========
    public static class Builder {
        private Long id;
        private Customer customer;
        private LocalDate orderDate;
        private OrderStatus orderStatus;
        private Money totalAmount;
        private List<OrderItem> orderItems = new ArrayList<>();

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder setOrderDate(LocalDate orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public Builder setOrderStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public Builder setTotalAmount(Money totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder setOrderItems(List<OrderItem> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        public Builder copy(Order order) {
            this.id = order.id;
            this.customer = order.customer;
            this.orderDate = order.orderDate;
            this.orderStatus = order.orderStatus;
            this.totalAmount = order.totalAmount;
            this.orderItems = order.orderItems;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
