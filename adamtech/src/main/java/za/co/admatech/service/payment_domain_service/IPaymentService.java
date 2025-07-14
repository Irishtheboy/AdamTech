package za.co.admatech.service.payment_domain_service;

import za.co.admatech.domain.Payment;
import za.co.admatech.service.IService;

import java.util.List;

public interface IPaymentService extends IService<Payment, Long> {
   List<Payment> getPayments();
}
