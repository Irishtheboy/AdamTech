/*





Cart.java



Cart Class



Author: Teyana Raubenheimer (230237622)



Date: 11 May 2025 */ package za.co.admatech.domain;

import jakarta.persistence.*; import java.util.List;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "cart", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<CartItem> cartItems;

    public Customer getCustomer() {
        return customer;
    }

    public Long getCartID() {
        return cartID;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartID='" + cartID + '\'' +
                ", customer=" + customer +
                ", cartItems=" + cartItems +
                '}';
    }

    protected Cart() {
    }

    protected Cart(Builder builder) {
        this.cartID = builder.cartID;
        this.customer = builder.customer;
        this.cartItems = builder.cartItems;
    }

    public static class Builder {
        private Long cartID;
        private Customer customer;
        private List<CartItem> cartItems;

        public Builder setCartID(Long cartID) {
            this.cartID = cartID;
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

        public Builder copy(Cart cart) {
            this.cartID = cart.getCartID();
            this.customer = cart.getCustomer();
            this.cartItems = cart.getCartItems();
            return this;
        }

        public Cart build() {
            return new Cart(this);
        }
    }

}