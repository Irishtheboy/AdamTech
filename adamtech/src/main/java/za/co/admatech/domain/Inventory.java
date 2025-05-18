/*
 * Inventory.java
 * Inventory Class
 * Author: Seymour Lawrence (230185991)
 * Date: 11 May 2025
 */
package za.co.admatech.domain;

import za.co.admatech.domain.enums.InventoryStatus;

public class Inventory {

    private String id;
    private String productId;
    private int quantity;
    private InventoryStatus inventoryStatus;

    public Inventory() {
    }

    private Inventory(Builder builder) {
        this.id = builder.id;
        this.productId = builder.productId;
        this.quantity = builder.quantity;
        this.inventoryStatus = builder.inventoryStatus;
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

    public InventoryStatus getInventoryStatus() {
        return inventoryStatus;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", quantity=" + quantity +
                ", inventoryStatus=" + inventoryStatus +
                '}';
    }

    public static class Builder {
        private String id;
        private String productId;
        private int quantity;
        private InventoryStatus inventoryStatus;

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

        public Builder setInventoryStatus(InventoryStatus inventoryStatus) {
            this.inventoryStatus = inventoryStatus;
            return this;
        }

        public Builder copy(Inventory inventory) {
            this.id = inventory.id;
            this.productId = inventory.productId;
            this.quantity = inventory.quantity;
            this.inventoryStatus = inventory.inventoryStatus;
            return this;
        }

        public Inventory build() {
            return new Inventory(this);
        }
    }
}
