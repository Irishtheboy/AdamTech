package za.co.admatech.domain;

public class OrderItem {

    private String id;
    private String productId;
    private int quantity;
    private Money unitPrice;

    public OrderItem() {

    }

    private OrderItem(Builder builder) {
        this.id = builder.id;
        this.productId = builder.productId;
        this.quantity = builder.quantity;
        this.unitPrice = builder.unitPrice;
    }

    public String getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public Money getUnitPrice() {
        return unitPrice;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }

    public static class Builder {
        private String id;
        private String productId;
        private int quantity;
        private Money unitPrice;

        public void setId(String id) {
            this.id = id;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public void setUnitPrice(Money unitPrice) {
            this.unitPrice = unitPrice;
        }

        public Builder copy(OrderItem orderItem) {
            this.id = orderItem.id;
            this.productId = orderItem.productId;
            this.quantity = orderItem.quantity;
            this.unitPrice = orderItem.unitPrice;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }
}
