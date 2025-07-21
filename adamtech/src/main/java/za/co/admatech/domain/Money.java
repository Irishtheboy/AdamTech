/*





Money.java



Money POJO class



Author: FN Lukhele (221075127)



Date: 10 May 2025 */ package za.co.admatech.domain;

import jakarta.persistence.Embeddable; import jakarta.validation.constraints.NotNull; import java.math.BigDecimal;

@Embeddable public class Money {
    @NotNull private double amount;

    @NotNull
    private String currency;

    public Money() {
    }

    public Money(Builder builder) {
        this.amount = builder.amount;
        this.currency = builder.currency;
    }

    public Money(double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "Money{" +
                "amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }

    public static class Builder {
        private double amount;
        private String currency;

        public Builder setAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder setCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public Money build() {
            return new Money(this);
        }

        public Builder copy(Money money) {
            this.amount = money.amount;
            this.currency = money.currency;
            return this;
        }
    }

}