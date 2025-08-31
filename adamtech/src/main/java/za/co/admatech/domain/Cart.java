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
    private Long cartId;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CartItem> cartItems = new ArrayList<>();

    public Cart() {
        this.cartItems = new ArrayList<>();
    }

    public Cart(Builder builder) {
        this.cartId = builder.cartId;
        this.customer = builder.customer;
        this.cartItems = builder.cartItems != null ? builder.cartItems : new ArrayList<>();
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
                ", customer=" + customer +
                ", cartItems=" + cartItems +
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
            this.cartItems = cart.cartItems;
            return this;
        }

        public Cart build() {
            Cart cart = new Cart(this);


            if (cart.getCartItems() != null) {
                for (CartItem item : cart.getCartItems()) {
                    item.setCart(cart);
                }
            }

            return cart;
        }
    }
}
