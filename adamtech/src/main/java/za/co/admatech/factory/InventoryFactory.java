package za.co.admatech.factory;

import za.co.admatech.domain.Inventory;
import za.co.admatech.domain.enums.InventoryStatus;

public class InventoryFactory {

    public static Inventory createInventory( int quantity, InventoryStatus inventoryStatus) {
        return new Inventory.Builder()


                .setQuantity(quantity)
                .setInventoryStatus(inventoryStatus)
                .build();
    }
}