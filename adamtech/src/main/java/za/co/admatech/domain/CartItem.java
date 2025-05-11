package za.co.admatech.domain;

public class CartItem {
    private String id;
    private String productID;
    private int quantity;
    private Cart cartID;

    public CartItem() {

    }

    public CartItem (Builder builder) {
        this.id = builder.id;
        this.productID = builder.productID;
        this.quantity = builder.quantity;
        this.cartID = builder.cartID;
    }


    public String getId() {
        return id;
    }

    public String getProductID() {
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
                "id='" + id + '\'' +
                ", productID='" + productID + '\'' +
                ", quantity=" + quantity +
                ", cartID=" + cartID +
                '}';
    }

    public static class Builder {
        private String id;
        private String productID;
        private int quantity;
        private Cart cartID;

        public Builder setId(String id) {
            this.id = id;
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

        public Builder setCartID(Cart cartID) {
            this.cartID = cartID;
            return this;
        }

        public Builder copy (CartItem cartItem) {
            this.id = cartItem.getId();
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
