/*
IAddressService.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025 */
package za.co.admatech.service.address_domain_service;

import za.co.admatech.domain.Address; import za.co.admatech.service.IService; import java.util.List;

public interface IAddressService extends IService<Address, Long> {
    List getAll();
}