package za.co.admatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.admatech.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}