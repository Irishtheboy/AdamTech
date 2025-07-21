/*





CustomerService.java



Author: Rorisang Makgana (230602363)



Date: 11 May 2025 */ package za.co.admatech.service.customer_domain_service;

import jakarta.persistence.EntityNotFoundException; import jakarta.transaction.Transactional; import org.springframework.stereotype.Service; import za.co.admatech.domain.Customer; import za.co.admatech.repository.CustomerRepository; import za.co.admatech.util.Helper;

import java.util.List;

@Service public class CustomerService implements ICustomerService { private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Customer create(Customer customer) {
        if (!Helper.isValidCustomer(customer)) {
            throw new IllegalArgumentException("Invalid customer data");
        }
        return customerRepository.save(customer);
    }

    @Override
    public Customer read(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer with ID " + id + " not found"));
    }

    @Override
    @Transactional
    public Customer update(Customer customer) {
        if (!Helper.isValidCustomer(customer) || customer.getCustomerID() == null) {
            throw new IllegalArgumentException("Invalid customer data or missing ID");
        }
        if (!customerRepository.existsById(customer.getCustomerID())) {
            throw new EntityNotFoundException("Customer with ID " + customer.getCustomerID() + " not found");
        }
        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        if (!customerRepository.existsById(id)) {
            return false;
        }
        customerRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

}