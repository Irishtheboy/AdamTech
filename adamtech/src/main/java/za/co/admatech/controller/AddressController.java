package za.co.admatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.domain.Address;
import za.co.admatech.service.address_domain_service.AddressService;

@RestController
@RequestMapping("/address")

public class AddressController {
    private final AddressService addressService;
    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/create")
    public Address create(@RequestBody Address address){
        return addressService.create(address);
    }

    @GetMapping("read/{addressID}")
    public Address read(@PathVariable Long addressID){
        return addressService.read(addressID);
    }

    @PutMapping("/update")
    public Address update(@RequestBody Address address){
        return addressService.update(address);
    }

    @RequestMapping("/delete/{addressID}")
    public boolean delete(@PathVariable Long addressID){
        return addressService.delete(addressID);
    }
}
