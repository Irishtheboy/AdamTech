package za.co.admatech.factory;


import za.co.admatech.domain.Money;
import za.co.admatech.domain.Payment;
import za.co.admatech.domain.enums.PaymentStatus;
import za.co.admatech.util.Helper;


import java.time.LocalDateTime;

public class PaymentFactory {
    public static Payment createPayment(String paymentId, String orderId, LocalDateTime paymentDate, Money amount, PaymentStatus paymentStatus) {
        if (Helper.isNullOrEmpty(paymentId) || Helper.isNullOrEmpty(orderId) || paymentDate == null || amount == null || !Helper.isValidPaymentStatus(paymentStatus)) {
            return null;
        }
        return new Payment.Builder()
                .paymentId(paymentId)
                .orderId(orderId)
                .paymentDate(paymentDate)
                .amount(amount)
                .paymentStatus(paymentStatus)
                .build();
    }

    public static Payment createPayment(String orderId, LocalDateTime paymentDate, Money amount, PaymentStatus paymentStatus) {
        return createPayment(Helper.generateId(), orderId, paymentDate, amount, paymentStatus);
    }
}