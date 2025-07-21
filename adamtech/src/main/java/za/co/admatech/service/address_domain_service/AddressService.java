/*
AddressService.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025 */
package za.co.admatech.service.address_domain_service;

import jakarta.persistence.EntityNotFoundException; import jakarta.transaction.Transactional; import org.springframework.stereotype.Service; import za.co.admatech.domain.Address; import za.co.admatech.repository.AddressRepository; import za.co.admatech.util.Helper;

import java.util.List;

@Service public class AddressService implements IAddressService { private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional
    public Address create(Address address) {
        if (!Helper.isValidAddress(address)) {
            throw new IllegalArgumentException("Invalid address data");
        }
        return addressRepository.save(address);
    }

    @Override
    public Address read(Long addressID) {
        return addressRepository.findById(addressID)
                .orElseThrow(() -> new EntityNotFoundException("Address with ID " + addressID + " not found"));
    }

    @Override
    @Transactional
    public Address update(Address address) {
        if (!Helper.isValidAddress(address) || address.getAddressID() == null) {
            throw new IllegalArgumentException("Invalid address data or missing ID");
        }
        if (!addressRepository.existsById(address.getAddressID())) {
            throw new EntityNotFoundException("Address with ID " + address.getAddressID() + " not found");
        }
        return addressRepository.save(address);
    }

    @Override
    @Transactional
    public boolean delete(Long addressID) {
        if (!addressRepository.existsById(addressID)) {
            return false;
        }
        addressRepository.deleteById(addressID);
        return true;
    }

    @Override
    public List<Address> getAll() {
        return addressRepository.findAll();
    }

}