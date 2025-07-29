/*
IPaymentService.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025 */
package za.co.admatech.service.payment_domain_service;

import za.co.admatech.domain.Payment;
import za.co.admatech.service.IService;
import java.util.List;

public interface IPaymentService extends IService<Payment, String> {
    List<Payment> getAll();
}