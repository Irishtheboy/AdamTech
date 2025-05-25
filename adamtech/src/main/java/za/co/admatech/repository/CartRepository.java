package za.co.admatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.admatech.domain.Cart;
@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
}
