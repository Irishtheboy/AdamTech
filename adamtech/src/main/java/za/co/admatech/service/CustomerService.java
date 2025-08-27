package za.co.admatech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.admatech.domain.Customer;
import za.co.admatech.domain.Payment;
import za.co.admatech.repository.CustomerRepository;

import java.util.List;

@Service
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer read(Long customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }

    @Override
    public Customer update(Customer customer) {
        return customerRepository.save(customer);
    }
    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }


    @Override
    public boolean delete(Long customerId) {
        if (!customerRepository.existsById(customerId)) return false;
        customerRepository.deleteById(customerId);
        return true;
    }
}
