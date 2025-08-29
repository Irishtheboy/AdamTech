package za.co.admatech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.admatech.domain.Address;
import za.co.admatech.repository.AddressRepository;

import java.util.List;

@Service
public class AddressService implements IAddressService {
    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {this.addressRepository = addressRepository;}

    @Override
    public Address create(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address read(Long addressID) {
        return addressRepository.findById(addressID).orElse(null);
    }

    @Override
    public Address update(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public boolean delete(Long addressID) {
        addressRepository.deleteById(addressID);
        return true;
    }

    @Override
    public List<Address> getAll() {
        return addressRepository.findAll();
    }
}
