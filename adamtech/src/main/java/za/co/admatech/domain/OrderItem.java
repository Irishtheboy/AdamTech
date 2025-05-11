/*OrderItem POJO Class
  Naqeebah Khan 219099073
  10 May 2025*/

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

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setProductId(String productId) {
            this.productId = productId;
            return this;
        }

        public Builder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setUnitPrice(Money unitPrice) {
            this.unitPrice = unitPrice;
            return this;
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
