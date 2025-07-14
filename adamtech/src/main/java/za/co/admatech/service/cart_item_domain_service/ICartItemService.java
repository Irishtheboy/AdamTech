/*ICartItemService.java
  Author: Teyana Raubenheimer (230237622)
  Date: 23 May 2025
 */

package za.co.admatech.service.cart_item_domain_service;

import za.co.admatech.domain.CartItem;
import za.co.admatech.service.IService;

import java.util.List;

public interface ICartItemService extends IService<CartItem, Long> {
    List<CartItem> getAll();
}
