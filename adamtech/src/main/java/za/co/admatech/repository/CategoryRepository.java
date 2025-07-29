package za.co.admatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.admatech.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}