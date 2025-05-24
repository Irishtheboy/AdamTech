package za.co.admatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.admatech.domain.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, String> {

}
