/*
MoneyService.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025 */
package za.co.admatech.service.money_domain_service;

import org.springframework.stereotype.Service;
import za.co.admatech.domain.Money;
import za.co.admatech.util.Helper;

import java.util.ArrayList;
import java.util.List;

@Service
public class MoneyService implements IMoneyService {

    // Note: Since Money is embeddable, we won't use a repository directly.
    // This service acts as a utility for Money validation and creation.

    @Override
    public Money create(Money money) {
        if (!isValidMoney(money)) {
            throw new IllegalArgumentException("Invalid money data");
        }
        return money.copy(); // Return a new instance to ensure immutability
    }

    @Override
    public Money update(Money money) {
        if (!isValidMoney(money)) {
            throw new IllegalArgumentException("Invalid money data");
        }
        return money.copy(); // Return a new instance with updated values
    }

    @Override
    public boolean isValidMoney(Money money) {
        return money != null && money.getAmount() != null && !Helper.isNullOrEmpty(money.getCurrency());
    }

    @Override
    public List<Money> getAll() {
        // This method is included for interface consistency but may not apply
        // to embeddable types. Could be used if Money is stored in a collection context.
        return new ArrayList<>(); // Placeholder; implement if needed
    }
}