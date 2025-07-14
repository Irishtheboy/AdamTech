/*
 * Inventory.java
 * Inventory Class
 * Author: Seymour Lawrence (230185991)
 * Date: 11 May 2025
 */
package za.co.admatech.domain;

import jakarta.persistence.*;
import za.co.admatech.domain.enums.InventoryStatus;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product productId;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private InventoryStatus inventoryStatus;

    public Inventory() {
    }

    private Inventory(Builder builder) {
        this.id = builder.id;
        this.productId = builder.productId;
        this.quantity = builder.quantity;
        this.inventoryStatus = builder.inventoryStatus;
    }

    public Long getId() {
        return id;
    }

    public Product getProductId() {
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
        private Long id;
        private Product productId;
        private int quantity;
        private InventoryStatus inventoryStatus;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setProductId(Product productId) {
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