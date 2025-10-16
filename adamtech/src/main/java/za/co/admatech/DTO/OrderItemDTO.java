package za.co.admatech.DTO;

import za.co.admatech.domain.OrderItem;
import za.co.admatech.domain.Product;

public class OrderItemDTO {
    private Long orderItemId;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double price;

    public OrderItemDTO(OrderItem item) {
        if (item != null) {
            this.orderItemId = item.getId();
            this.quantity = item.getQuantity();

            Product product = item.getProduct();
            if (product != null) {
                this.productId = product.getProductId();
                this.productName = product.getName();
                this.price = item.getUnitPrice() != null ? item.getUnitPrice().getAmount() : 0.0;
            } else {
                this.productName = "Unknown Product";
                this.price = 0.0;
            }
        }
    }

    // Getters and setters
    public Long getOrderItemId() { return orderItemId; }
    public void setOrderItemId(Long orderItemId) { this.orderItemId = orderItemId; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}