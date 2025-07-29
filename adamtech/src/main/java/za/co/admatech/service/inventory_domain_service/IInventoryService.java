/*
IInventoryService.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025 */
package za.co.admatech.service.inventory_domain_service;

import za.co.admatech.domain.Inventory;
import za.co.admatech.service.IService;
import java.util.List;

public interface IInventoryService extends IService<Inventory, String> {
    List<Inventory> getAll();
}