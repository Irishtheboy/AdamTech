package za.co.admatech.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

@Embeddable
public class Money {
    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency;

    // Public no-arg constructor
    public Money() {}



    private Money(Builder builder) {
        this.amount = builder.amount;
        this.currency = builder.currency;
    }

    public static class Builder {
        private BigDecimal amount;
        private String currency;

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public Money build() {
            return new Money(this);
        }
    }

    public Money copy() {
        return new Builder()
                .amount(this.amount)
                .currency(this.currency)
                .build();
    }

    // Getters and setters
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
}