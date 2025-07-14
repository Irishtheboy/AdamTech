/*
 * IInventoryService.java
 * IInventoryService Class
 * Author: Seymour Lawrence (230185991)
 * Date: 25 May 2025
 */
package za.co.admatech.service.inventory_domain_service;

import za.co.admatech.domain.Inventory;
import za.co.admatech.service.IService;

import java.util.List;

public interface IInventoryService extends IService<Inventory, Long> {
    List<Inventory> getAll();
}