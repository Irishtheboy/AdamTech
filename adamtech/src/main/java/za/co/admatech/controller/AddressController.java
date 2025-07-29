/*
AddressController.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025 */
package za.co.admatech.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*; import za.co.admatech.domain.Address; import za.co.admatech.service.address_domain_service.AddressService;

import java.util.List;


    @RestController
    @RequestMapping("/api/address")
    public class AddressController {

        @Autowired
        private AddressService addressService;

        @PostMapping("/create")
        public ResponseEntity<Address> create(@RequestBody Address address) {
            return ResponseEntity.ok(addressService.create(address));
        }

        @GetMapping("/{id}")
        public ResponseEntity<Address> read(@PathVariable Long id) {
            return ResponseEntity.ok(addressService.read(id));
        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
            addressService.delete(id);
            return ResponseEntity.ok().build();
        }
    }
