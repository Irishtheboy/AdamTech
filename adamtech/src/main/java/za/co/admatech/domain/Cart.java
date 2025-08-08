/*CartItem.java
  CartItem Class
  Author: Teyana Raubenheimer (230237622)
  Date: 11 May 2025
 */

package za.co.admatech.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartID;
    private String customerID;
    private String cartItemID;

    @OneToMany
    @JoinColumn(name = "cart_id")
    private List<CartItem> cartItems;


    public Cart() {

    }

    public Cart(Builder builder) {
        this.cartID = builder.cartID;
        this.customerID = builder.customerID;
        this.cartItemID = builder.cartItemID;
    }

    public Long getCartID() {
        return cartID;
    }

    public String  getCustomerID() {
        return customerID;
    }

    public String  getCartItemID() {
        return cartItemID;
    }


    @Override
    public String toString() {
        return "Cart{" +
                "cartID='" + cartID + '\'' +
                ", customerID='" + customerID + '\'' +
                ", cartItemID='" + cartItemID + '\'' +
                '}';
    }

    public static class Builder {
        private Long cartID;
        private String  customerID;
        private String  cartItemID;

        public Builder setCartID(Long cartID) {
            this.cartID = cartID;
            return this;

        }

        public Builder setCustomerID(String  customerID) {

            this.customerID = customerID;
            return this;
        }

        public Builder setCartItemID(String cartItemID) {
            this.cartItemID = cartItemID;
            return this;
        }

        public Builder copy(Cart cart) {
            this.cartID = cartID;
            this.customerID = customerID;
            this.cartItemID = cartItemID;
            return this;

        }

        public Cart build() {
            return new Cart(this);
        }



    }
}
