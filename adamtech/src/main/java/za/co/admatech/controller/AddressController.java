package za.co.admatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.domain.Address;
import za.co.admatech.service.AddressService;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    private final AddressService service;

    @Autowired
    public AddressController(AddressService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<Address> create(@RequestBody Address address) {
        return ResponseEntity.ok(service.create(address));
    }

    @GetMapping("/read/{addressID}")
    public ResponseEntity<Address> read(@PathVariable Long addressID) {
        Address address = service.read(addressID);
        return address != null ? ResponseEntity.ok(address) : ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{addressId}")
    public ResponseEntity<Address> update(@PathVariable Long addressId, @RequestBody Address address) {
        Address addressWithId = new Address.Builder()
                .copy(address)
                .setAddressId(addressId)
                .build();
                
        Address readAddress = service.read(addressWithId.getAddressId());
        return ResponseEntity.ok(service.read(readAddress.getAddressId()));
    }
    
    @DeleteMapping("/delete/{addressID}")
    public ResponseEntity<Void> delete(@PathVariable Long addressID) {
        boolean deleted = service.delete(addressID);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Address>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
