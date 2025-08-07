package za.co.admatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.admatech.domain.CartOrder;

@Repository
public interface CartOrderRepository extends JpaRepository<CartOrder, Long> {
}