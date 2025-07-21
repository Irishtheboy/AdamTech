/*





MoneyFactory.java



Author: FN Lukhele (221075127) */ package za.co.admatech.factory;

import za.co.admatech.domain.Money; import za.co.admatech.util.Helper;

import java.math.BigDecimal;

public class MoneyFactory {
    public static Money createMoney(double amount,
                                    String currency) {

        return new Money.Builder()
                .setAmount(amount)
                .setCurrency(currency)
                .build();
    }
}