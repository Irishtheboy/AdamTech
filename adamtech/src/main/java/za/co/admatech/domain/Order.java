package za.co.admatech.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import za.co.admatech.domain.enums.OrderStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // 'order' is reserved in SQL
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "orderId"
)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Embedded
    @Column(name = "total_amount")
    private Money totalAmount;

    //@JsonManagedReference
    /*Removed the @JsonManagedReference as we already have the @JsonIdentityInfo which handles 
     * the serialization and deserialization of the Cart and CartItem relationship.
     */    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order() {
    }

    protected Order(Builder builder) {
        this.orderId = builder.orderId;
        this.customer = builder.customer;
        this.orderDate = builder.orderDate;
        this.orderStatus = builder.orderStatus;
        this.totalAmount = builder.totalAmount;
        this.orderItems = builder.orderItems != null ? builder.orderItems : new ArrayList<>();
        // Link each OrderItem back to this Order
        this.orderItems.forEach(item -> item.setOrder(this));
    }

    public Long getOrderId() {
        return orderId;
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

    @Override
    public String toString() {
        return "Order{" +
                "id=" + orderId +
                ", customer=" + customer +
                ", orderDate=" + orderDate +
                ", orderStatus=" + orderStatus +
                ", totalAmount=" + totalAmount +
                ", orderItems=" + orderItems +
                '}';
    }

    public static class Builder {
        private Long orderId;
        private Customer customer;
        private LocalDate orderDate;
        private OrderStatus orderStatus;
        private Money totalAmount;
        private List<OrderItem> orderItems = new ArrayList<>();

        public Builder setOrderId(Long orderId) {
            this.orderId = orderId;
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
            this.orderId = order.orderId;
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
