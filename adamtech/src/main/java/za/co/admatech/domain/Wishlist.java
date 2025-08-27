package za.co.admatech.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "wishlist")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_id")
    private Long wishlistId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Default constructor
    public Wishlist() {
    }

    // Constructor (used by Builder)
    public Wishlist(Customer customer, Product product, LocalDateTime createdAt) {
        this.customer = customer;
        this.product = product;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // ========================
    // Builder Pattern (Fluent)
    // ========================
    public static class Builder {
        private Customer customer;
        private Product product;
        private LocalDateTime createdAt;

        public Builder customer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Wishlist build() {
            return new Wishlist(customer, product, createdAt);
        }
    }
}
