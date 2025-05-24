/*CartItem.java
  CartItem Class
  Author: Teyana Raubenheimer (230237622)
  Date: 11 May 2025
 */

package za.co.admatech.domain;

public class CartItem {
    private String cartItemID;
    private Product productID;
    private int quantity;
    private Cart cartID;

    public CartItem() {

    }

    public CartItem (Builder builder) {
        this.cartItemID = builder.cartItemID;
        this.productID = builder.productID;
        this.quantity = builder.quantity;
        this.cartID = builder.cartID;
    }


    public String getCartItemID() {
        return cartItemID;
    }

    public Product getProductID() {
        return productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public Cart getCartID() {
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
        private String cartItemID;
        private Product productID;
        private int quantity;
        private Cart cartID;

        public Builder setCartItemID(String cartItemID) {
            this.cartItemID = cartItemID;
            return this;
        }

        public Builder setProductID(Product productID) {
            this.productID = productID;
            return this;
        }

        public Builder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setCartID(Cart cartID) {
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
