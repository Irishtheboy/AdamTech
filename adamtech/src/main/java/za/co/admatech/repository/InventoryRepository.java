package za.co.admatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.admatech.domain.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}