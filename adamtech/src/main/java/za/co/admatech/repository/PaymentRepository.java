package za.co.admatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.admatech.domain.Payment;

public interface PaymentRepository extends JpaRepository<Payment, String> {
    // Additional query methods can be defined here if needed
}
