package za.co.admatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.domain.Inventory;
import za.co.admatech.service.IInventoryService;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@CrossOrigin(origins = "http://localhost:8080")
public class InventoryController {

    private final IInventoryService service;

    @Autowired
    public InventoryController(IInventoryService service) {
        this.service = service;
    }

    // Create
    @PostMapping("/create")
    public ResponseEntity<Inventory> create(@RequestBody Inventory inventory) {
        Inventory created = service.create(inventory);
        return ResponseEntity.ok(created);
    }

    // Read by ID
    @GetMapping("/read/{id}")
    public ResponseEntity<Inventory> read(@PathVariable Long id) {
        Inventory inventory = service.read(id);
        if (inventory == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(inventory);
    }

    // Update
    @PutMapping("/update/{id}")
    public ResponseEntity<Inventory> update(@PathVariable Long id, @RequestBody Inventory inventory) {
        Inventory existing = service.read(id);
        if (existing == null) return ResponseEntity.notFound().build();

        Inventory updated = service.update(inventory);
        return ResponseEntity.ok(updated);
    }

    // Delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Inventory existing = service.read(id);
        if (existing == null) return ResponseEntity.notFound().build();

        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Get all
    @GetMapping("/getAll")
    public ResponseEntity<List<Inventory>> getAll() {
        List<Inventory> list = service.getAll();
        return ResponseEntity.ok(list);
    }
}
