package za.co.admatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.admatech.domain.Money;

@Repository
public interface MoneyRepository extends JpaRepository<Money, Long> {
}