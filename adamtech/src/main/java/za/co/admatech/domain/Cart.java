package za.co.admatech.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "cartId")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long cartId;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CartItem> cartItems = new ArrayList<>();

    // Getters only - no public setters for immutability
    public Long getCartId() {
        return cartId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<CartItem> getCartItems() {
        return cartItems != null ? cartItems : new ArrayList<>();
    }

    // Default constructor for JPA
    public Cart() {
        this.cartItems = new ArrayList<>();
    }

    // Protected constructor for Builder pattern
    protected Cart(Builder builder) {
        this.cartId = builder.cartId;
        this.customer = builder.customer;
        this.cartItems = builder.cartItems != null ? builder.cartItems : new ArrayList<>();
    }

    // Setters for JPA/Hibernate and CartItem relationship management (used by services)
    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", customer=" + (customer != null ? customer.getEmail() : "null") +
                ", cartItems=" + (cartItems != null ? cartItems.size() + " items" : "null") +
                '}';
    }

    public static class Builder {
        private Long cartId;
        private Customer customer;
        private List<CartItem> cartItems = new ArrayList<>();

        public Builder setCartId(Long cartId) {
            this.cartId = cartId;
            return this;
        }

        public Builder setCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder setCartItems(List<CartItem> cartItems) {
            this.cartItems = cartItems != null ? cartItems : new ArrayList<>();
            return this;
        }

        public Builder copy(Cart cart) {
            this.cartId = cart.cartId;
            this.customer = cart.customer;
            this.cartItems = new ArrayList<>(cart.cartItems != null ? cart.cartItems : new ArrayList<>());
            return this;
        }

        public Cart build() {
            Cart cart = new Cart(this);

            // Establish bidirectional relationships for existing CartItems
            if (cart.getCartItems() != null) {
                List<CartItem> updatedItems = new ArrayList<>();
                for (CartItem item : cart.getCartItems()) {
                    CartItem updatedItem = new CartItem.Builder()
                            .copy(item)
                            .setCart(cart)
                            .build();
                    updatedItems.add(updatedItem);
                }
                cart.setCartItems(updatedItems);
            }

            return cart;
        }
    }
}
