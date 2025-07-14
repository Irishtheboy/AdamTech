package za.co.admatech.service.payment_domain_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.admatech.domain.Payment;
import za.co.admatech.repository.PaymentRepository;
import za.co.admatech.service.IService;

import java.util.List;

@Service
public class PaymentService implements IPaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment create(Payment payment) {
        return paymentRepository.save(payment);
    }
    @Override
    public Payment read(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }
    @Override
    public Payment update(Payment payment) {
        return paymentRepository.save(payment);
    }
    @Override
    public boolean delete(Long id) {
        paymentRepository.deleteById(id);
        return true;
    }
    public List<Payment> getPayments() {
        return paymentRepository.findAll();
    }
}
