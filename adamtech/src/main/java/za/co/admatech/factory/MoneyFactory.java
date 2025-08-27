package za.co.admatech.factory;

import za.co.admatech.domain.Money;
import za.co.admatech.util.Helper;

public class MoneyFactory {

    public static Money createMoney(int amount, String currency) {
        if (amount <= 0) {
            return null; // Invalid amount
        }
        if (Helper.isNullOrEmpty(currency)) {
            return null; // Invalid currency
        }

        return new Money.Builder()
                .setAmount(amount)
                .setCurrency(currency)
                .build();
    }

    public static Money buildMoney(int amount, String currency) {
        return new Money.Builder()
                .setAmount(amount)
                .setCurrency(currency)
                .build();
    }
}
