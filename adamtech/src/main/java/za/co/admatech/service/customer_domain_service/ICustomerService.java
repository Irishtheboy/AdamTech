/*
ICustomerService.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025 */
package za.co.admatech.service.customer_domain_service;

import za.co.admatech.domain.Customer;
import za.co.admatech.service.IService;
import java.util.List;

public interface ICustomerService extends IService<Customer, String> {
    List<Customer> getAll();
}