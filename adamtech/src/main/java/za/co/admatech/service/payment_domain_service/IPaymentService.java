/*





IPaymentService.java



Author: FN Lukhele (221075127)



Date: 24 May 2025 */ package za.co.admatech.service.payment_domain_service;

import za.co.admatech.domain.Payment; import za.co.admatech.service.IService; import java.util.List;

public interface IPaymentService extends IService<Payment, Long> { List getAll(); }