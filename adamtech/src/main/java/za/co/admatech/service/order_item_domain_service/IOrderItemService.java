/*





IOrderItemService.java



Author: Naqeebah Khan (219099073)



Date: 24 May 2025 */ package za.co.admatech.service.order_item_domain_service;

import za.co.admatech.domain.OrderItem; import za.co.admatech.service.IService; import java.util.List;

public interface IOrderItemService extends IService<OrderItem, Long> { List getAll(); }