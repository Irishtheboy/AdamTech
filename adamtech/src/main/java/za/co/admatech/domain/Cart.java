package za.co.admatech.domain;

public class Cart {
    private String cartID;
    private Customer customerID;
    private CartItem cartItemID;


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

    public Customer getCustomerID() {
        return customerID;
    }

    public CartItem getCartItemID() {
        return cartItemID;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartID='" + cartID + '\'' +
                ", customerID='" + customerID + '\'' +
                ", cartItemID=" + cartItemID +
                '}';
    }

    public static class Builder {
        private String cartID;
        private Customer customerID;
        private CartItem cartItemID;

        public Builder setCartID(String cartID) {
            this.cartID = cartID;
            return this;

        }

        public Builder setCustomerID(Customer customerID) {
            this.customerID = customerID;
            return this;
        }

        public Builder setCartItemID(CartItem cartItemID) {
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
