package za.co.admatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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

    @GetMapping("/getAll")
    public Iterable<Address> getAll() {
        return service.getAll();
    }

    @GetMapping("/user/{email}")
    public List<Address> getAddressesByUserEmail(@PathVariable String email) {
        return service.findByCustomerEmail(email);
    }
}
