package za.co.admatech.domain;

import jakarta.persistence.*;

@Entity
public class OrderItem {
    @Id
    @Column(nullable = false)
    private String orderItemId;

    @Column(nullable = false)
    private String productId;

    @Column(nullable = false)
    private int quantity;

    @Embedded
    @Column(nullable = false)
    private Money unitPrice;

    @ManyToOne
    @JoinColumn(name = "orderId", referencedColumnName = "id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "productId", insertable = false, updatable = false)
    private Product product;

    // Public no-arg constructor
    public OrderItem() {}



    private OrderItem(Builder builder) {
        this.orderItemId = builder.orderItemId;
        this.productId = builder.productId;
        this.quantity = builder.quantity;
        this.unitPrice = builder.unitPrice;
        this.order = builder.order;
        this.product = builder.product;
    }

    public static class Builder {
        private String orderItemId;
        private String productId;
        private int quantity;
        private Money unitPrice;
        private Order order;
        private Product product;

        public Builder orderItemId(String orderItemId) {
            this.orderItemId = orderItemId;
            return this;
        }

        public Builder productId(String productId) {
            this.productId = productId;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder unitPrice(Money unitPrice) {
            this.unitPrice = unitPrice;
            return this;
        }

        public Builder order(Order order) {
            this.order = order;
            return this;
        }

        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }

    public OrderItem copy() {
        return new Builder()
                .orderItemId(this.orderItemId)
                .productId(this.productId)
                .quantity(this.quantity)
                .unitPrice(this.unitPrice)
                .order(this.order)
                .product(this.product)
                .build();
    }

    // Getters and setters
    public String getOrderItemId() { return orderItemId; }
    public void setOrderItemId(String orderItemId) { this.orderItemId = orderItemId; }
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public Money getUnitPrice() { return unitPrice; }
    public void setUnitPrice(Money unitPrice) { this.unitPrice = unitPrice; }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}