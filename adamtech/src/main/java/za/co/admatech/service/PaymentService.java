package za.co.admatech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.admatech.domain.Payment;
import za.co.admatech.repository.PaymentRepository;

import java.util.List;

@Service
public class PaymentService implements IPaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment create(Payment payment) {
        Payment saved = paymentRepository.save(payment);
        paymentRepository.flush(); // force JPA to hit the DB and generate ID
        return saved;
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
    @Override
    public List<Payment> getAll(){
        return paymentRepository.findAll();
    }


}
