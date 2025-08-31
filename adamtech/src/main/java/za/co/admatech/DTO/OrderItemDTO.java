package za.co.admatech.DTO;

import za.co.admatech.domain.OrderItem;
import za.co.admatech.domain.Product;

// This DTO is used to send order item details to the frontend
public class OrderItemDTO {

    private Long id;            // OrderItem ID
    private String productName;  // Product name
    private double unitPrice;    // Price per unit
    private int quantity;        // Quantity ordered

    // Optional: include product ID if needed for reference
    private Long productId;

    // Constructor that maps OrderItem entity to DTO
    public OrderItemDTO(OrderItem item) {
        if (item != null) {
            this.id = item.getId();
            this.quantity = item.getQuantity();

            // Extract product info safely
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

    // Getters
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
