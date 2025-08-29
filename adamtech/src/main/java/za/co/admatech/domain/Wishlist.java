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
    public Wishlist() {}

    // Constructor (used by Builder)
    private Wishlist(Builder builder) {
        this.wishlistId = builder.wishlistId;
        this.customer = builder.customer;
        this.product = builder.product;
        this.createdAt = builder.createdAt;
    }

    // Getters
    public Long getWishlistId() {
        return wishlistId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Product getProduct() {
        return product;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // ================
    // Builder Pattern
    // ================
    public static class Builder {
        private Long wishlistId;
        private Customer customer;
        private Product product;
        private LocalDateTime createdAt;

        public Builder setWishlistId(Long wishlistId) {
            this.wishlistId = wishlistId;
            return this;
        }

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

        // ✅ copy method
        public Builder copy(Wishlist wishlist) {
            this.wishlistId = wishlist.wishlistId;
            this.customer = wishlist.customer;
            this.product = wishlist.product;
            this.createdAt = wishlist.createdAt;
            return this;
        }

        public Wishlist build() {
            return new Wishlist(this);
        }
    }

    @Override
    public String toString() {
        return "Wishlist{" +
                "wishlistId=" + wishlistId +
                ", customer=" + (customer != null ? customer.getEmail() : "null") +
                ", product=" + (product != null ? product.getName() : "null") +
                ", createdAt=" + createdAt +
                '}';
    }
}
