/*
PaymentService.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025 */
package za.co.admatech.service.payment_domain_service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import za.co.admatech.domain.Payment;
import za.co.admatech.repository.PaymentRepository;
import za.co.admatech.util.Helper;

import java.util.List;

@Service
public class PaymentService implements IPaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional
    public Payment create(Payment payment) {
        if (payment == null || !Helper.isValidPaymentStatus(payment.getPaymentStatus())) {
            throw new IllegalArgumentException("Invalid payment data");
        }
        return paymentRepository.save(payment);
    }

    @Override
    public Payment read(String id) {
        try {
            Long longId = Long.valueOf(id);
            return paymentRepository.findById(longId)
                    .orElseThrow(() -> new EntityNotFoundException("Payment with ID " + id + " not found"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid payment ID format: " + id, e);
        }
    }

    @Override
    @Transactional
    public Payment update(Payment payment) {
        if (payment.getPaymentId() == null || !Helper.isValidPaymentStatus(payment.getPaymentStatus())) {
            throw new IllegalArgumentException("Invalid payment data or missing ID");
        }
        try {
            Long longId = Long.valueOf(payment.getPaymentId());
            if (!paymentRepository.existsById(longId)) {
                throw new EntityNotFoundException("Payment with ID " + payment.getPaymentId() + " not found");
            }
            return paymentRepository.save(payment);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid payment ID format: " + payment.getPaymentId(), e);
        }
    }

    @Override
    @Transactional
    public boolean delete(String id) {
        try {
            Long longId = Long.valueOf(id);
            if (!paymentRepository.existsById(longId)) {
                return false;
            }
            paymentRepository.deleteById(longId);
            return true;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid payment ID format: " + id, e);
        }
    }

    @Override
    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }
}