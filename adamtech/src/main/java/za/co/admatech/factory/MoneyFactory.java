package za.co.admatech.factory;


import za.co.admatech.domain.Money;
import za.co.admatech.util.Helper;

import java.math.BigDecimal;

public class MoneyFactory {
    public static Money createMoney(BigDecimal amount, String currency) {
        if (amount == null || Helper.isNullOrEmpty(currency)) {
            return null;
        }
        return new Money.Builder()
                .amount(amount)
                .currency(currency)
                .build();
    }
}