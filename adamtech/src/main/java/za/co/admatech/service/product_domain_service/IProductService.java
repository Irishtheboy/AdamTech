/*





IProductService.java



Author: Seymour Lawrence (230185991)



Date: 25 May 2025 */ package za.co.admatech.service.product_domain_service;

import za.co.admatech.domain.Product; import za.co.admatech.service.IService; import java.util.List;

public interface IProductService extends IService<Product, Long> { List getAll(); }