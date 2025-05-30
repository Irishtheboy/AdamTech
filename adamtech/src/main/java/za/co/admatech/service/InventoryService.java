/*
 * InventoryService.java
 * InventoryService Class
 * Author: Seymour Lawrence (230185991)
 * Date: 25 May 2025
 */
package za.co.admatech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.admatech.domain.Inventory;
import za.co.admatech.repository.InventoryRepository;
import java.util.List;

@Service
public class InventoryService implements IInventoryService {

    private static IInventoryService service;

    @Autowired
    private InventoryRepository repository;

    @Override
    public Inventory create(Inventory inventory) {
        return this.repository.save(inventory);
    }

    @Override
    public Inventory read(String id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public Inventory update(Inventory inventory) {
        return this.repository.save(inventory);
    }

    @Override
    public boolean delete(String id) {
        this.repository.deleteById(id);
        return true;
    }

    @Override
    public List<Inventory> getAll() {
        return this.repository.findAll();
    }
}