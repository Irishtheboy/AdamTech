/*
CustomerService.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025 */
package za.co.admatech.service.customer_domain_service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import za.co.admatech.domain.Customer;
import za.co.admatech.repository.CustomerRepository;
import za.co.admatech.util.Helper;

import java.util.List;

@Service
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Customer create(Customer customer) {
        if (!Helper.isValidEmail(customer.getEmail())) {
            throw new IllegalArgumentException("Invalid email address");
        }
        return customerRepository.save(customer);
    }

    @Override
    public Customer read(String customerID) {
        try {
            Long longId = Long.valueOf(customerID);
            return customerRepository.findById(longId)
                    .orElseThrow(() -> new EntityNotFoundException("Customer with ID " + customerID + " not found"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid customer ID format: " + customerID, e);
        }
    }

    @Override
    @Transactional
    public Customer update(Customer customer) {
        if (customer.getCustomerId() == null || !Helper.isValidEmail(customer.getEmail())) {
            throw new IllegalArgumentException("Invalid customer data or missing ID");
        }
        try {
            Long longId = Long.valueOf(customer.getCustomerId());
            if (!customerRepository.existsById(longId)) {
                throw new EntityNotFoundException("Customer with ID " + customer.getCustomerId() + " not found");
            }
            return customerRepository.save(customer);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid customer ID format: " + customer.getCustomerId(), e);
        }
    }

    @Override
    @Transactional
    public boolean delete(String customerID) {
        try {
            Long longId = Long.valueOf(customerID);
            if (!customerRepository.existsById(longId)) {
                return false;
            }
            customerRepository.deleteById(longId);
            return true;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid customer ID format: " + customerID, e);
        }
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }
}