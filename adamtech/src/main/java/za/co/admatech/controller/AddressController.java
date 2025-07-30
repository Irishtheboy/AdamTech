/*
AddressController.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025 */
package za.co.admatech.controller;

import jakarta.validation.Valid; import org.springframework.http.HttpStatus; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*; import za.co.admatech.domain.Address; import za.co.admatech.service.address_domain_service.AddressService;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@CrossOrigin(origins = "*") // Adjust origins as needed for production public class AddressController { private final AddressService addressService;
public class AddressController{
    private AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

@PostMapping("/create_address")
public ResponseEntity<Address> create(@Valid @RequestBody Address address) {
    Address created = addressService.create(address);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
}

@GetMapping("/{addressID}")
public ResponseEntity<Address> read(@PathVariable Long addressID) {
    Address address = addressService.read(addressID);
    return ResponseEntity.ok(address);
}

@PutMapping
public ResponseEntity<Address> update(@Valid @RequestBody Address address) {
    Address updated = addressService.update(address);
    return ResponseEntity.ok(updated);
}

@DeleteMapping("/{addressID}")
public ResponseEntity<Void> delete(@PathVariable Long addressID) {
    boolean deleted = addressService.delete(addressID);
    return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
}

@GetMapping
public ResponseEntity<List<Address>> getAll() {
    return ResponseEntity.ok(addressService.getAll());
}

}