/*Order.java
  Order Class
  Author: Naqeebah Khan (219099073)
  Date: 10 May 2025
 */

package za.co.admatech.domain;

import jakarta.persistence.*;
import za.co.admatech.domain.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table (name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    CHANGES MADE BY RORI:
    - removed CustomerID
    */
    private LocalDate orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Embedded
    private Money totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @ManyToOne
    private Customer customer;

    public Order() {
    }

    public Order(Builder builder) {
        this.id = builder.id;
        this.orderDate = builder.orderDate;
        this.orderStatus = builder.orderStatus;
        this.totalAmount = builder.totalAmount;
        this.orderItems = builder.orderItems;
    }

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

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", customer='" + customer + '\'' +
                ", orderDate=" + orderDate +
                ", orderStatus='" + orderStatus + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                '}';
    }

    public static class Builder {
        private Long id;
        private LocalDate orderDate;
        private OrderStatus orderStatus;
        private Money totalAmount;
        private List<OrderItem> orderItems;
        private Customer customer;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        /*
        CHANGES MADE BY RORI:
        - changed it from CustomerID to Customer
         */
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


