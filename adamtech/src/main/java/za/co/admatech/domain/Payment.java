package za.co.admatech.domain;

import jakarta.persistence.*;
import za.co.admatech.domain.enums.PaymentStatus;

import java.time.LocalDate;

/* Payment.java

     Payment POJO class

     Author: FN Lukhele (221075127)

     Date: 10 May 2025

*/
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order orderId;
    private LocalDate paymentDate;
    @Embedded
    private Money amount;
    private PaymentStatus paymentStatus;

    public Long getId() {
        return id;
    }

    public Order getOrderId() {
        return orderId;
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
                "Id='" + id + '\'' +
                ", orderId=" + orderId +
                ", paymentDate=" + paymentDate +
                ", amount=" + amount +
                ", paymentStatus=" + paymentStatus +
                '}';
    }

    protected Payment() {
    }
    protected Payment(Builder builder) {
        id = builder.id;
        orderId = builder.orderId;
        paymentDate = builder.paymentDate;
        amount = builder.amount;
        paymentStatus = builder.paymentStatus;
    }

    public static class Builder{
        private Long id;
        private Order orderId;
        private LocalDate paymentDate;
        private Money amount;
        private PaymentStatus paymentStatus;

        public Builder id(Long id){
            this.id = id;
            return this;
        }
        public Builder orderId(Order orderId){
            this.orderId = orderId;
            return this;
        }
        public Builder paymentDate(LocalDate paymentDate){
            this.paymentDate = paymentDate;
            return this;
        }
        public Builder amount(Money amount){
            this.amount = amount;
            return this;
        }
        public Builder paymentStatus(PaymentStatus paymentStatus){
            this.paymentStatus = paymentStatus;
            return this;
        }
        public Builder copy(Payment payment){
            this.id = payment.id;
            this.orderId = payment.orderId;
            this.paymentDate = payment.paymentDate;
            this.amount = payment.amount;
            this.paymentStatus = payment.paymentStatus;
            return this;
        }

        public Payment build(){
            return new Payment(this);
        }
    }
}
