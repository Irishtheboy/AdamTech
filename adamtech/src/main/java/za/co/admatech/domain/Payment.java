package za.co.admatech.domain;

import za.co.admatech.domain.enums.PaymentStatus;

import java.time.LocalDate;

public class Payment {
    private String Id;
    private String orderId;
    private LocalDate paymentDate;
    private double amount; // change to class once its complete
    private PaymentStatus paymentStatus;

    public Payment() {
    }

    private Payment(Builder builder) {
        this.Id = builder.Id;
        this.orderId = builder.orderId;
        this.paymentDate = builder.paymentDate;
        this.amount = builder.amount;
        this.paymentStatus = builder.paymentStatus;
    }

    public String getId() {
        return Id;
    }

    public String getOrderId() {
        return orderId;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "Id='" + Id + '\'' +
                ", orderId='" + orderId + '\'' +
                ", paymentDate=" + paymentDate +
                ", amount=" + amount +
                ", paymentStatus=" + paymentStatus +
                '}';
    }

    public static class Builder{
        private String Id;
        private String orderId;
        private LocalDate paymentDate;
        private double amount; // change to class once its complete
        private PaymentStatus paymentStatus;

        public Builder setPaymentStatus(PaymentStatus paymentStatus) {
            this.paymentStatus = paymentStatus;
            return this;
        }

        public Builder setAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder setPaymentDate(LocalDate paymentDate) {
            this.paymentDate = paymentDate;
            return this;
        }

        public Builder setOrderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder setId(String id) {
            Id = id;
            return this;
        }

        public Payment build(Payment payment) {
            payment.Id = this.Id;
            payment.orderId = this.orderId;
            payment.paymentDate = this.paymentDate;
            payment.amount = this.amount;
            payment.paymentStatus = this.paymentStatus;
            return payment;
        }

        public Payment build() {
            return new Payment(this);
        }
    }
}
