package za.co.admatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.admatech.domain.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}