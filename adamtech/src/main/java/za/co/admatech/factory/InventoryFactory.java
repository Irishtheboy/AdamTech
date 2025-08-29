package za.co.admatech.factory;

import java.util.List;

import za.co.admatech.domain.Inventory;
import za.co.admatech.domain.Product;
import za.co.admatech.domain.enums.InventoryStatus;

public class InventoryFactory {

    public static Inventory createInventory(List<Product> products, int quantity, InventoryStatus inventoryStatus) {
        return new Inventory.Builder()
            .setProduct(products)
            .setQuantity(quantity)
            .setInventoryStatus(inventoryStatus)
            .build();
    }
}