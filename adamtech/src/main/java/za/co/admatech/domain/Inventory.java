/*
 * Inventory.java
 * Inventory Class
 * Author: Seymour Lawrence (230185991)
 * Date: 11 May 2025
 */
package za.co.admatech.domain;

import java.util.List;

import jakarta.persistence.*;
import za.co.admatech.domain.enums.InventoryStatus;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long inventoryId;

    /*Updated the relationship of the Products within the Inventory as we have to stored 
    * multiple products within the inventory.
    */
    @OneToOne
    @JoinColumn(name = "product_id")
    private List<Product> products;

    @Column(name = "quantity")
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "inventory_status")
    private InventoryStatus inventoryStatus;

    public Inventory() {
    }

    private Inventory(Builder builder) {
        this.inventoryId = builder.inventoryId;
        this.products = builder.products;
        this.quantity = builder.quantity;
        this.inventoryStatus = builder.inventoryStatus;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public List<Product> getProduct() {
        return products;
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
                "id=" + inventoryId +
                ", product=" + products +
                ", quantity=" + quantity +
                ", inventoryStatus=" + inventoryStatus +
                '}';
    }

    public static class Builder {
        private Long inventoryId;
        private List<Product> products;
        private int quantity;
        private InventoryStatus inventoryStatus;

        public Builder setInventoryId(Long inventoryId) {
            this.inventoryId = inventoryId;
            return this;
        }

        public Builder setProduct(List <Product> products) {
            this.products = products;
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
            this.inventoryId = inventory.inventoryId;
            this.products = inventory.products;
            this.quantity = inventory.quantity;
            this.inventoryStatus = inventory.inventoryStatus;
            return this;
        }

        public Inventory build() {
            return new Inventory(this);
        }
    }
}