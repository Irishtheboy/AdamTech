/*
 * Payment.java
 * Payment Class
 * Author: [Assumed Author]
 * Date: 10 May 2025
 */
package za.co.admatech.domain;

import jakarta.persistence.*;
import za.co.admatech.domain.enums.PaymentStatus;

import java.time.LocalDate;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Embedded
    @Column(name = "amount")
    private Money amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    public Payment() {
    }

    private Payment(Builder builder) {
        this.paymentId = builder.paymentId;
        this.order = builder.order;
        this.paymentDate = builder.paymentDate;
        this.amount = builder.amount;
        this.paymentStatus = builder.paymentStatus;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public Order getOrder() {
        return order;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public Money getAmount() {
        return amount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + paymentId +
                ", order=" + order +
                ", paymentDate=" + paymentDate +
                ", amount=" + amount +
                ", paymentStatus=" + paymentStatus +
                '}';
    }

    public static class Builder {
        private Long paymentId;
        private Order order;
        private LocalDate paymentDate;
        private Money amount;
        private PaymentStatus paymentStatus;

        public Builder setPaymentId(Long paymentId) {
            this.paymentId = paymentId;
            return this;
        }

        public Builder setOrder(Order order) {
            this.order = order;
            return this;
        }

        public Builder setPaymentDate(LocalDate paymentDate) {
            this.paymentDate = paymentDate;
            return this;
        }

        public Builder setAmount(Money amount) {
            this.amount = amount;
            return this;
        }

        public Builder setPaymentStatus(PaymentStatus paymentStatus) {
            this.paymentStatus = paymentStatus;
            return this;
        }

        public Builder copy(Payment payment) {
            this.paymentId = payment.paymentId;
            this.order = payment.order;
            this.paymentDate = payment.paymentDate;
            this.amount = payment.amount;
            this.paymentStatus = payment.paymentStatus;
            return this;
        }

        public Payment build() {
            return new Payment(this);
        }
    }
}