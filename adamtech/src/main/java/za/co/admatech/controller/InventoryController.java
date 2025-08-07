/*
InventoryController.java
Author: Naqeebah Khan (219099073)
Date: 03 June 2025 */
package za.co.admatech.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.domain.Inventory;
import za.co.admatech.service.inventory_domain_service.IInventoryService;

import java.util.List;

@RestController
@RequestMapping("/inventories")
@CrossOrigin(origins = "*")
public class InventoryController {
    private final IInventoryService inventoryService;

    public InventoryController(IInventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseEntity<Inventory> create(@Valid @RequestBody Inventory inventory) {
        Inventory created = inventoryService.create(inventory);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> read(@PathVariable String id) {
        Inventory inventory = inventoryService.read(id);
        return ResponseEntity.ok(inventory);
    }

    @PutMapping
    public ResponseEntity<Inventory> update(@Valid @RequestBody Inventory inventory) {
        Inventory updated = inventoryService.update(inventory);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean deleted = inventoryService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Inventory>> getAll() {
        return ResponseEntity.ok(inventoryService.getAll());
    }
}