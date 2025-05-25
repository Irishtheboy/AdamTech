package za.co.admatech.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import za.co.admatech.domain.*;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findAddress(Long addressID);
}
