package za.co.admatech.domain;

import jakarta.persistence.*;
import za.co.admatech.domain.enums.InventoryStatus;

@Entity
public class Inventory {
    @Id
    @Column(nullable = false)
    private String inventoryId;

    @Column(nullable = false)
    private String productId;

    @Column(nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InventoryStatus inventoryStatus;

    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "productId", insertable = false, updatable = false)
    private Product product;

    // Public no-arg constructor
    public Inventory() {}



    private Inventory(Builder builder) {
        this.inventoryId = builder.inventoryId;
        this.productId = builder.productId;
        this.quantity = builder.quantity;
        this.inventoryStatus = builder.inventoryStatus;
        this.product = builder.product;
    }

    public static class Builder {
        private String inventoryId;
        private String productId;
        private int quantity;
        private InventoryStatus inventoryStatus;
        private Product product;

        public Builder inventoryId(String inventoryId) {
            this.inventoryId = inventoryId;
            return this;
        }

        public Builder productId(String productId) {
            this.productId = productId;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder inventoryStatus(InventoryStatus inventoryStatus) {
            this.inventoryStatus = inventoryStatus;
            return this;
        }

        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        public Inventory build() {
            return new Inventory(this);
        }
    }

    public Inventory copy() {
        return new Builder()
                .inventoryId(this.inventoryId)
                .productId(this.productId)
                .quantity(this.quantity)
                .inventoryStatus(this.inventoryStatus)
                .product(this.product)
                .build();
    }

    // Getters and setters
    public String getInventoryId() { return inventoryId; }
    public void setInventoryId(String inventoryId) { this.inventoryId = inventoryId; }
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public InventoryStatus getInventoryStatus() { return inventoryStatus; }
    public void setInventoryStatus(InventoryStatus inventoryStatus) { this.inventoryStatus = inventoryStatus; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}