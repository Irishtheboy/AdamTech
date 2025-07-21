/*





PaymentFactory.java



Author: FN Lukhele (221075127) */ package za.co.admatech.factory;

import za.co.admatech.domain.Money; import za.co.admatech.domain.Order; import za.co.admatech.domain.Payment; import za.co.admatech.domain.enums.PaymentStatus; import za.co.admatech.util.Helper;

import java.time.LocalDate;

public class PaymentFactory { public static Payment createPayment(Long id, Order order, LocalDate paymentDate, Money amount, PaymentStatus paymentStatus) { if (paymentDate == null || !Helper.isValidLocalDate(paymentDate) || paymentStatus == null || amount == null || order == null) { throw new IllegalArgumentException("Payment date, status, amount, and order must be valid"); } if (!Helper.isValidOrder(order)) { throw new IllegalArgumentException("Invalid order"); } return new Payment.Builder() .setId(id) .setOrder(order) .setPaymentDate(paymentDate) .setAmount(amount) .setPaymentStatus(paymentStatus) .build(); } }