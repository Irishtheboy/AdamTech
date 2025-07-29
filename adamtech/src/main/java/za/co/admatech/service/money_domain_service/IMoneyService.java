/*
IMoneyService.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025 */
package za.co.admatech.service.money_domain_service;

import za.co.admatech.domain.Money;
import za.co.admatech.service.IService;
import java.util.List;

public interface IMoneyService {
    Money create(Money money);
    Money update(Money money);
    boolean isValidMoney(Money money); // Utility method to validate Money
    List<Money> getAll(); // Assuming a collection context if needed
}