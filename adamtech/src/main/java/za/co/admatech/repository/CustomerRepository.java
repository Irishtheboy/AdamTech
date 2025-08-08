package za.co.admatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.admatech.domain.Customer;
import java.util.Optional;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    //Optional<Customer> findCustomerById(String id);
}