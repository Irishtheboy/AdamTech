/*





InventoryService.java



Author: Seymour Lawrence (230185991)



Date: 25 May 2025 */ package za.co.admatech.service.inventory_domain_service;

import jakarta.persistence.EntityNotFoundException; import jakarta.transaction.Transactional; import org.springframework.stereotype.Service; import za.co.admatech.domain.Inventory; import za.co.admatech.repository.InventoryRepository; import za.co.admatech.util.Helper;

import java.util.List;

@Service public class InventoryService implements IInventoryService { private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    @Transactional
    public Inventory create(Inventory inventory) {
        if (inventory == null || inventory.getProduct() == null || inventory.getQuantity() < 0 || inventory.getInventoryStatus() == null) {
            throw new IllegalArgumentException("Invalid inventory data");
        }
        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory read(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventory with ID " + id + " not found"));
    }

    @Override
    @Transactional
    public Inventory update(Inventory inventory) {
        if (inventory == null || inventory.getId() == null || inventory.getProduct() == null || inventory.getQuantity() < 0 || inventory.getInventoryStatus() == null) {
            throw new IllegalArgumentException("Invalid inventory data or missing ID");
        }
        if (!inventoryRepository.existsById(inventory.getId())) {
            throw new EntityNotFoundException("Inventory with ID " + inventory.getId() + " not found");
        }
        return inventoryRepository.save(inventory);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        if (!inventoryRepository.existsById(id)) {
            return false;
        }
        inventoryRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Inventory> getAll() {
        return inventoryRepository.findAll();
    }

}