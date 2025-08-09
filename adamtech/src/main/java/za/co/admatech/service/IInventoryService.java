/*
 * IInventoryService.java
 * IInventoryService Class
 * Author: Seymour Lawrence (230185991)
 * Date: 25 May 2025
 */
package za.co.admatech.service;

import za.co.admatech.domain.Inventory;
import java.util.List;

public interface IInventoryService extends IService<Inventory, Long> {
    List<Inventory> getAll();
}