package za.co.admatech.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "wishlist")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "wishlistId")
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

    @Override
    public String toString() {
        return "Wishlist{" +
                "wishlistId=" + wishlistId +
                ", customer=" + (customer != null ? customer.getEmail() : "null") +
                ", product=" + (product != null ? product.getName() : "null") +
                ", createdAt=" + createdAt +
                '}';
    }

    public Wishlist() {}

    private Wishlist(Builder builder) {
        this.wishlistId = builder.wishlistId;
        this.customer = builder.customer;
        this.product = builder.product;
        this.createdAt = builder.createdAt;
    }


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

    public static class Builder {
        private Long wishlistId;
        private Customer customer;
        private Product product;
        private LocalDateTime createdAt;

        public Builder setWishlistId(Long wishlistId) {
            this.wishlistId = wishlistId;
            return this;
        }

        public Builder setCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder setProduct(Product product) {
            this.product = product;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
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


}
