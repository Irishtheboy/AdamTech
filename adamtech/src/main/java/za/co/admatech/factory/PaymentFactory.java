package za.co.admatech.factory;

import za.co.admatech.domain.Money;
import za.co.admatech.domain.Payment;
import za.co.admatech.domain.enums.PaymentStatus;
import za.co.admatech.util.Helper;

import java.time.LocalDate;

import static za.co.admatech.util.Helper.generateId;

public class PaymentFactory {
    public static Payment createPayment(LocalDate paymentDate, String orderId, Money amount, PaymentStatus paymentStatus) {



        if (!Helper.isValidLocalDate(paymentDate)) {
            return null;
        }
        if (Helper.isNullOrEmpty(orderId)) {
            return null;
        }
        if (!Helper.isValidPaymentStatus(paymentStatus)) {
            return null;
        }

        if (amount == null) {
            return null;
        }



        return new Payment.Builder()
                .setOrderId(orderId)
                .setPaymentDate(paymentDate)
                .setAmount(amount)
                .setPaymentStatus(paymentStatus)
                .build();
    }
}
