package za.co.admatech.factory;


import za.co.admatech.domain.Inventory;
import za.co.admatech.domain.Product;
import za.co.admatech.domain.enums.InventoryStatus;
import za.co.admatech.util.Helper;

public class InventoryFactory {
    public static Inventory createInventory(String inventoryId, String productId, int quantity, InventoryStatus inventoryStatus, Product product) {
        if (Helper.isNullOrEmpty(inventoryId) || Helper.isNullOrEmpty(productId) || quantity < 0 || !Helper.isValidInventoryStatus(inventoryStatus) || product == null) {
            return null;
        }
        return new Inventory.Builder()
                .inventoryId(inventoryId)
                .productId(productId)
                .quantity(quantity)
                .inventoryStatus(inventoryStatus)
                .product(product)
                .build();
    }

    public static Inventory createInventory(String productId, int quantity, InventoryStatus inventoryStatus, Product product) {
        return createInventory(Helper.generateId(), productId, quantity, inventoryStatus, product);
    }
}