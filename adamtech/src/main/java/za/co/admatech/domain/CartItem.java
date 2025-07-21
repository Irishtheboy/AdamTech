/*





CartItem.java



CartItem Class



Author: Teyana Raubenheimer (230237622)



Date: 11 May 2025 */ package za.co.admatech.domain;

import jakarta.persistence.*; import jakarta.validation.constraints.Min;

@Entity public class CartItem { @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long cartItemID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Min(0)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    protected CartItem() {
    }

    protected CartItem(Builder builder) {
        this.cartItemID = builder.cartItemID;
        this.product = builder.product;
        this.quantity = builder.quantity;
        this.cart = builder.cart;
    }

    public Long getCartItemID() {
        return cartItemID;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public Cart getCart(){
        return cart;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "cartItemID='" + cartItemID + '\'' +
                ", product='" + product + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    public static class Builder {
        private Long cartItemID;
        private Product product;
        private int quantity;
        private Cart cart;

        public Builder setCartItemID(Long cartItemID) {
            this.cartItemID = cartItemID;
            return this;
        }

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
            this.cartItemID = cartItem.getCartItemID();
            this.product = cartItem.getProduct();
            this.quantity = cartItem.getQuantity();
            this.cart = cartItem.getCart();
            return this;
        }

        public CartItem build() {
            return new CartItem(this);
        }
    }

}