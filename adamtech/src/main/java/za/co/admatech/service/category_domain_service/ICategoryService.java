/*
ICategoryService.java
Author: Rorisang Makgana (230602363)
Date: 11 May 2025 */
package za.co.admatech.service.category_domain_service;

import za.co.admatech.domain.Category;
import za.co.admatech.service.IService;
import java.util.List;

public interface ICategoryService extends IService<Category, String> {
    List<Category> getAll();
}