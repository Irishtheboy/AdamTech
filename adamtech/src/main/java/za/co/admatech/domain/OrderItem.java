package za.co.admatech.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
 property = "orderItemId")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    @Embedded
    @Column(name = "unit_price")
    private Money unitPrice;


    //@JsonBackReference
    /*Removed the @JsonBackReference as we already have the @JsonIdentityInfo which handles 
     * the serialization and deserialization of the Cart and CartItem relationship.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem() {
    }

    protected OrderItem(Builder builder) {
        this.orderItemId = builder.orderItemId;
        this.product = builder.product;
        this.quantity = builder.quantity;
        this.unitPrice = builder.unitPrice;
        this.order = builder.order;
    }

    public Long getOrderItemId() {
        return orderItemId;
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

    // For bidirectional linking
    void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + orderItemId +
                ", product=" + product +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }

    public static class Builder {
        private Long orderItemId;
        private Product product;
        private int quantity;
        private Money unitPrice;
        private Order order;

        public Builder setOrderItemId(Long orderItemId) {
            this.orderItemId = orderItemId;
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
            this.orderItemId = orderItem.orderItemId;
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
