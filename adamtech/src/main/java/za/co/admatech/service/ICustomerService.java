package za.co.admatech.service;

import za.co.admatech.domain.Customer;

import java.util.Optional;

public interface ICustomerService extends IService<Customer, Long>{
    Optional<Customer> findByEmail(String email);

}
