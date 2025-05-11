package za.co.admatech.domain;

public class Cart {
    private String cartID;
    private String custmerID;


    public Cart() {

    }

    public Cart(Builder builder) {
        this.cartID = builder.cartID;
        this.custmerID = builder.custmerID;
    }


    public String getCartID() {
        return cartID;
    }

    public String getCustmerID() {
        return custmerID;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartID='" + cartID + '\'' +
                ", custmerID='" + custmerID + '\'' +
                '}';
    }

    public static class Builder {
        private String cartID;
        private String custmerID;

        public Builder setCartID(String cartID) {
            this.cartID = cartID;
            return this;

        }

        public Builder setCustmerID(String custmerID) {
            this.custmerID = custmerID;
            return this;
        }

        public Builder copy(Cart cart) {
            this.cartID = cartID;
            this.custmerID = custmerID;
            return this;

        }

        public Cart build() {
            return new Cart(this);
        }


    }
}
