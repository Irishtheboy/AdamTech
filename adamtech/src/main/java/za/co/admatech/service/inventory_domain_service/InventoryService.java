/*
InventoryService.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025 */
package za.co.admatech.service.inventory_domain_service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import za.co.admatech.domain.Inventory;
import za.co.admatech.repository.InventoryRepository;
import za.co.admatech.util.Helper;

import java.util.List;

@Service
public class InventoryService implements IInventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    @Transactional
    public Inventory create(Inventory inventory) {
        if (inventory == null || inventory.getQuantity() < 0 || !Helper.isValidInventoryStatus(inventory.getInventoryStatus())) {
            throw new IllegalArgumentException("Invalid inventory data");
        }
        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory read(String id) {
        try {
            Long longId = Long.valueOf(id);
            return inventoryRepository.findById(longId)
                    .orElseThrow(() -> new EntityNotFoundException("Inventory with ID " + id + " not found"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid inventory ID format: " + id, e);
        }
    }

    @Override
    @Transactional
    public Inventory update(Inventory inventory) {
        if (inventory.getInventoryId() == null || inventory.getQuantity() < 0 || !Helper.isValidInventoryStatus(inventory.getInventoryStatus())) {
            throw new IllegalArgumentException("Invalid inventory data or missing ID");
        }
        try {
            Long longId = Long.valueOf(inventory.getInventoryId());
            if (!inventoryRepository.existsById(longId)) {
                throw new EntityNotFoundException("Inventory with ID " + inventory.getInventoryId() + " not found");
            }
            return inventoryRepository.save(inventory);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid inventory ID format: " + inventory.getInventoryId(), e);
        }
    }

    @Override
    @Transactional
    public boolean delete(String id) {
        try {
            Long longId = Long.valueOf(id);
            if (!inventoryRepository.existsById(longId)) {
                return false;
            }
            inventoryRepository.deleteById(longId);
            return true;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid inventory ID format: " + id, e);
        }
    }

    @Override
    public List<Inventory> getAll() {
        return inventoryRepository.findAll();
    }
}