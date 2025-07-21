/*





PaymentService.java



Author: FN Lukhele (221075127)



Date: 24 May 2025 */ package za.co.admatech.service.payment_domain_service;

import jakarta.persistence.EntityNotFoundException; import jakarta.transaction.Transactional; import org.springframework.stereotype.Service; import za.co.admatech.domain.Payment; import za.co.admatech.repository.PaymentRepository; import za.co.admatech.util.Helper;

import java.util.List;

@Service public class PaymentService implements IPaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional
    public Payment create(Payment payment) {
        if (!Helper.isValidPayment(payment)) {
            throw new IllegalArgumentException("Invalid payment data");
        }
        return paymentRepository.save(payment);
    }

    @Override
    public Payment read(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment with ID " + id + " not found"));
    }

    @Override
    @Transactional
    public Payment update(Payment payment) {
        if (!Helper.isValidPayment(payment) || payment.getId() == null) {
            throw new IllegalArgumentException("Invalid payment data or missing ID");
        }
        if (!paymentRepository.existsById(payment.getId())) {
            throw new EntityNotFoundException("Payment with ID " + payment.getId() + " not found");
        }
        return paymentRepository.save(payment);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        if (!paymentRepository.existsById(id)) {
            return false;
        }
        paymentRepository.deleteById(id);
        return true;
    }

    @Override
    public List getAll() {
        return List.of();
    }
}
