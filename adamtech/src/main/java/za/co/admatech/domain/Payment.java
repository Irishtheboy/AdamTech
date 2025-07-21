/*





Payment.java



Payment POJO class



Author: FN Lukhele (221075127)



Date: 10 May 2025 */ package za.co.admatech.domain;

import jakarta.persistence.*; import jakarta.validation.constraints.NotNull; import za.co.admatech.domain.enums.PaymentStatus; import java.time.LocalDate;

@Entity @Table(name = "payment") public class Payment { @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @NotNull
    private LocalDate paymentDate;

    @Embedded
    private Money amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    public Long getId() {
        return id;
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
                "id='" + id + '\'' +
                ", order=" + order +
                ", paymentDate=" + paymentDate +
                ", amount=" + amount +
                ", paymentStatus=" + paymentStatus +
                '}';
    }

    protected Payment() {
    }

    protected Payment(Builder builder) {
        id = builder.id;
        order = builder.order;
        paymentDate = builder.paymentDate;
        amount = builder.amount;
        paymentStatus = builder.paymentStatus;
    }

    public static class Builder {
        private Long id;
        private Order order;
        private LocalDate paymentDate;
        private Money amount;
        private PaymentStatus paymentStatus;

        public Builder setId(Long id) {
            this.id = id;
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
            this.id = payment.id;
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