package za.co.admatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.admatech.domain.CartItem;

public interface CartItemRepository extends JpaRepository <CartItem, String > {
}
