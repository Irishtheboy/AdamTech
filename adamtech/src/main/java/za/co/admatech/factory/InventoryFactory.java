

/*





InventoryFactory.java



Author: Seymour Lawrence (230185991) */ package za.co.admatech.factory;

import za.co.admatech.domain.Inventory;
import za.co.admatech.domain.Product;
import za.co.admatech.domain.enums.InventoryStatus;
import za.co.admatech.util.Helper;

public class InventoryFactory {
    public static Inventory createInventory(Long id,
                                            Product product,
                                            int quantity,
                                            InventoryStatus inventoryStatus) {
        if (id == null ||
                id < 0 ||
                product == null ||
                quantity < 0 ||
                inventoryStatus == null) {
            throw new IllegalArgumentException("ID, product, quantity, and inventory status must be valid");
        }
        if (!Helper.isValidProduct(product)) {
            throw new IllegalArgumentException("Invalid product");
        }
        return new Inventory.Builder()
                .setId(id).setProduct(product)
                .setQuantity(quantity)
                .setInventoryStatus(inventoryStatus)
                .build();
    }
}