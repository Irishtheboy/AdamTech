/*OrderItem.java
  OrderItem Class
  Author: Naqeebah Khan (219099073)
  Date: 10 May 2025
 */

package za.co.admatech.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    @Embedded
    private Money unitPrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;

    public OrderItem() {

    }

    private OrderItem(Builder builder) {
        this.id = builder.id;
        this.product = builder.product;
        this.quantity = builder.quantity;
        this.unitPrice = builder.unitPrice;
        this.order = builder.order;

    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public Money getUnitPrice() {
        return unitPrice;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id='" + id + '\'' +
                ", productId='" + product + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }

    public static class Builder {
        private Long id;
        private Product product;
        private int quantity;
        private Money unitPrice;
        private Order order;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setProduct(Product product) {
            this.product = product;
            return this;
        }

        public Builder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setUnitPrice(Money unitPrice) {
            this.unitPrice = unitPrice;
            return this;
        }

        public Builder setOrder(Order order) {
            this.order = order;
            return this;
        }

        public Builder copy(OrderItem orderItem) {
            this.id = orderItem.id;
            this.product = orderItem.product;
            this.quantity = orderItem.quantity;
            this.unitPrice = orderItem.unitPrice;
            this.order = orderItem.order;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }
}
