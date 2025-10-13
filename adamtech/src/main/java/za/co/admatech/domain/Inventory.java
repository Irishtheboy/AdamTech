/*
 * Inventory.java
 * Inventory Class
 * Author: Seymour Lawrence (230185991)
 * Date: 11 May 2025
 */
package za.co.admatech.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import za.co.admatech.domain.enums.InventoryStatus;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    @Column(name = "quantity_in_stock")
    private int quantity;

    @Column(name = "reorder_level")
    private int reorderLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "inventory_status")
    private InventoryStatus inventoryStatus;

    public Inventory() {
    }

    private Inventory(Builder builder) {
        this.id = builder.id;
        this.product = builder.product;
        this.quantity = builder.quantity;
        this.reorderLevel = builder.reorderLevel;
        this.inventoryStatus = builder.inventoryStatus;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public InventoryStatus getInventoryStatus() {
        return inventoryStatus;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", product=" + product +
                ", quantity=" + quantity +
                ", inventoryStatus=" + inventoryStatus +
                '}';
    }

    public static class Builder {
        private Long id;
        private Product product;
        private int quantity;
        private int reorderLevel;
        private InventoryStatus inventoryStatus;

        public Builder setId(Long id) {
            this.id = id;
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

        public Builder setReorderLevel(int reorderLevel) {
            this.reorderLevel = reorderLevel;
            return this;
        }

        public Builder setInventoryStatus(InventoryStatus inventoryStatus) {
            this.inventoryStatus = inventoryStatus;
            return this;
        }

        public Builder copy(Inventory inventory) {
            this.id = inventory.id;
            this.product = inventory.product;
            this.quantity = inventory.quantity;
            this.reorderLevel = inventory.reorderLevel;
            this.inventoryStatus = inventory.inventoryStatus;
            return this;
        }

        public Inventory build() {
            return new Inventory(this);
        }
    }
}