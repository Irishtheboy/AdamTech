

package za.co.admatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.domain.Address;
import za.co.admatech.service.AddressService;

@RestController
@RequestMapping("/address")
public class AddressController {
    private AddressService service;

    @Autowired
    public AddressController(AddressService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Address create(@RequestBody Address address) {
        return service.create(address);
    }

    @GetMapping("/read/{addressID}")
    public Address read(@PathVariable Long addressID) {
        return service.read(addressID);
    }

    @PutMapping("/update")
    public Address update(@RequestBody Address address) {
        return service.update(address);
    }

    @DeleteMapping("/delete/{addressID}")
    public boolean delete(@PathVariable Long addressID) {
        return service.delete(addressID);
    }
}
