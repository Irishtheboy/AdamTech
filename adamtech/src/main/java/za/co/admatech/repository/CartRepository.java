package za.co.admatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.admatech.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, String> {
}
