package za.co.admatech.factory;

import za.co.admatech.domain.Inventory;
import za.co.admatech.domain.Product;
import za.co.admatech.domain.enums.InventoryStatus;
import za.co.admatech.util.Helper;

public class InventoryFactory {
    public static Inventory createInventory(
            Long id,
            Product productId,
            int quantity,
            InventoryStatus inventoryStatus
    ) {
        if (id == null || id < 0) return null;
        if (productId == null) return null;
        if (quantity < 0) return null;
        if (inventoryStatus == null || !Helper.isValidInventoryStatus(inventoryStatus)) return null;
        if (Helper.isValidProduct(productId) ) return null;

        return new Inventory.Builder()
                .setId(id)
                .setProductId(productId)
                .setQuantity(quantity)
                .setInventoryStatus(inventoryStatus)
                .build();
    }
}