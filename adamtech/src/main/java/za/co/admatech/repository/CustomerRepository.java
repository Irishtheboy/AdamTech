package za.co.admatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import za.co.admatech.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String>{
    
}
