package za.co.admatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.admatech.domain.CartItem;
@Repository
public interface CartItemRepository extends JpaRepository <CartItem, Long > {
}
