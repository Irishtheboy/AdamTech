package za.co.admatech.domain;

public class Cart {
    private String cartID;

    private Customer customerID;

    public Customer getCustomerID() {
        return customerID;
    }

    public String getCartID() {
        return cartID;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartID='" + cartID + '\'' +
                ", customerID='" + customerID + '\'' +
                '}';
    }

    protected Cart(Builder builder){
        this.cartID = builder.cartID;
        this.customerID = builder.customerID;
    }

    protected Cart(){}

    public static class Builder{
        private String cartID;
        private Customer customerID;

        public Builder setCartID(String cartID){
            this.cartID = cartID;
            return this;
        }

        public Builder setCustomerID(Customer customerID){
            this.customerID = customerID;
            return this;
        }

        public Builder copy(Cart cart){
            this.cartID = cartID;
            this.customerID = customerID;
            return this;
        }

        public Cart build(){
            return new Cart(this);
        }
    }
}
