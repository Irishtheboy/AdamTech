package za.co.admatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.admatech.domain.Payment;
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
