/*
 * Cart.java
 * Cart Class
 * Author: Teyana Raubenheimer (230237622)
 * Date: 11 May 2025
 */
package za.co.admatech.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems;

    public Cart() {}

    public Cart(Builder builder) {
        this.cartId = builder.cartId;
        this.customer = builder.customer;
        this.cartItems = builder.cartItems;
    }

    public Long getCartId() {
        return cartId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    // ✅ NEW: return products directly from cart items
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        if (cartItems != null) {
            for (CartItem item : cartItems) {
                products.add(item.getProduct());
            }
        }
        return products;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", customer=" + customer +
                ", cartItems=" + cartItems +
                '}';
    }

    public static class Builder {
        private Long cartId;
        private Customer customer;
        private List<CartItem> cartItems;

        public Builder setCartId(Long cartId) {
            this.cartId = cartId;
            return this;
        }

        public Builder setCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder setCartItems(List<CartItem> cartItems) {
            this.cartItems = cartItems;
            return this;
        }

        // NEW: add a single product as a CartItem
        public Builder addProduct(Product product) {
            if (this.cartItems == null) {
                this.cartItems = new ArrayList<>();
            }
            CartItem item = new CartItem.Builder()
                    .setProduct(product)
                    .setQuantity(1)
                    .build();
            this.cartItems.add(item);
            return this;
        }

        public Builder copy(Cart cart) {
            this.cartId = cart.cartId;
            this.customer = cart.customer;
            this.cartItems = cart.cartItems != null ? new ArrayList<>(cart.cartItems) : null;
            return this;
        }

        public Cart build() {
            return new Cart(this);
        }
    }
}
