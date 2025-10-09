package za.co.admatech.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@Entity
@Table(name = "cart_item")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "cartItemId")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long cartItemId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    @JsonBackReference
    private Cart cart;

    public Long getCartItemId() {
        return cartItemId;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public Cart getCart() {
        return cart;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "cartItemId=" + cartItemId +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }

    public CartItem() {}

    protected CartItem(Builder builder) {
        this.cartItemId = builder.cartItemId;
        this.product = builder.product;
        this.quantity = builder.quantity;
        this.cart = builder.cart;
    }

    // Package-private setter for Cart relationship
    void setCart(Cart cart) {
        this.cart = cart;
    }

    public static class Builder {
        private Long cartItemId;
        private Product product;
        private int quantity;
        private Cart cart;

        public Builder setProduct(Product product) {
            this.product = product;
            return this;
        }

        public Builder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setCart(Cart cart) {
            this.cart = cart;
            return this;
        }

        public Builder copy(CartItem cartItem) {
            this.cartItemId = cartItem.cartItemId;
            this.product = cartItem.product;
            this.quantity = cartItem.quantity;
            this.cart = cartItem.cart;
            return this;
        }

        public CartItem build() {
            return new CartItem(this);
        }
    }
}
