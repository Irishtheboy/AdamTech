/*





IOrderService.java



Author: Naqeebah Khan (219099073)



Date: 24 May 2025 */ package za.co.admatech.service.order_domain_service;

import za.co.admatech.domain.Order; import za.co.admatech.service.IService; import java.util.List;

public interface IOrderService extends IService<Order, Long> { List getAll(); }