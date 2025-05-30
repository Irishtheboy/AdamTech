/*CartItem.java
  CartItem Class
  Author: Teyana Raubenheimer (230237622)
  Date: 11 May 2025
 */

package za.co.admatech.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Cart {
    @Id
    private String cartID;
    private String customerID;
    private String cartItemID;


    public Cart() {

    }

    public Cart(Builder builder) {
        this.cartID = builder.cartID;
        this.customerID = builder.customerID;
        this.cartItemID = builder.cartItemID;
    }

    public String getCartID() {
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
        private String cartID;
        private String  customerID;
        private String  cartItemID;

        public Builder setCartID(String cartID) {
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
