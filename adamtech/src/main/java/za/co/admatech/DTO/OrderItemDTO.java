package za.co.admatech.DTO;

import za.co.admatech.domain.OrderItem;
import za.co.admatech.domain.Product;


public class OrderItemDTO {

    private Long id;
    private String productName;
    private double unitPrice;
    private int quantity;


    private Long productId;


    public OrderItemDTO(OrderItem item) {
        if (item != null) {
            this.id = item.getId();
            this.quantity = item.getQuantity();


            Product product = item.getProduct();
            if (product != null) {
                this.productId = product.getProductId();
                this.productName = product.getName();
                this.unitPrice = item.getUnitPrice() != null ? item.getUnitPrice().getAmount() : 0.0;
            } else {
                this.productName = "Unknown Product";
                this.unitPrice = 0.0;
            }
        }
    }


    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public Long getProductId() {
        return productId;
    }

    @Override
    public String toString() {
        return "OrderItemDTO{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", productId=" + productId +
                '}';
    }
}
