/*CartItem.java
  CartItem Class
  Author: Teyana Raubenheimer (230237622)
  Date: 11 May 2025
 */

package za.co.admatech.domain;

import jakarta.persistence.*;

@Entity
public class CartItem {
    @Id
    @GeneratedValue
    private Long cartItemID;
    private String  productID;
    private int quantity;
    private String cartID;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public CartItem() {

    }

    public CartItem (Builder builder) {
        this.cartItemID = builder.cartItemID;
        this.productID = builder.productID;
        this.quantity = builder.quantity;
        this.cartID = builder.cartID;
    }


    public Long getCartItemID() {
        return cartItemID;
    }

    public String getProductID() {
        return productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCartID() {
        return cartID;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "cartItemID='" + cartItemID + '\'' +
                ", productID='" + productID + '\'' +
                ", quantity=" + quantity +
                ", cartID=" + cartID +
                '}';
    }

    public static class Builder {
        private Long cartItemID;
        private String productID;
        private int quantity;
        private String cartID;

        public Builder setCartItemID(Long cartItemID) {
            this.cartItemID = cartItemID;
            return this;
        }

        public Builder setProductID(String productID) {
            this.productID = productID;
            return this;
        }

        public Builder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setCartID(String cartID) {
            this.cartID = cartID;
            return this;
        }

        public Builder copy (CartItem cartItem) {
            this.cartItemID = cartItem.getCartItemID();
            this.productID = cartItem.getProductID();
            this.quantity = cartItem.getQuantity();
            this.cartID = cartItem.getCartID();
            return this;
        }

        public CartItem build() {
            return new CartItem (this);
        }

    }
}
