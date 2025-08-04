/*
CartItem.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025 */
package za.co.admatech.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_item")
public class CartItem {
    @Id
    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String productId;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "cartId", referencedColumnName = "id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "productId", insertable = false, updatable = false)
    private Product product;

    // Public no-arg constructor
    public CartItem() {}

    private CartItem(Builder builder) {
        this.id = builder.id;
        this.productId = builder.productId;
        this.quantity = builder.quantity;
        this.cart = builder.cart;
        this.product = builder.product;
    }

    public static class Builder {
        private String id;
        private String productId;
        private int quantity;
        private Cart cart;
        private Product product;

        public Builder id(String id) {
            this.id = id;
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

        public Builder cart(Cart cart) {
            this.cart = cart;
            return this;
        }

        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        public CartItem build() {
            return new CartItem(this);
        }
    }

    public CartItem copy() {
        return new Builder()
                .id(this.id)
                .productId(this.productId)
                .quantity(this.quantity)
                .cart(this.cart)
                .product(this.product)
                .build();
    }

    // New method to get a Builder with current values
    public Builder getBuilder() {
        return new Builder()
                .id(this.id)
                .productId(this.productId)
                .quantity(this.quantity)
                .cart(this.cart)
                .product(this.product);
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}