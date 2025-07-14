/*ICartItemService.java
  Author: Teyana Raubenheimer (230237622)
  Date: 23 May 2025
 */

package za.co.admatech.service;

import za.co.admatech.domain.CartItem;

import java.util.List;

public interface ICartItemService extends IService<CartItem, Long>{
    List<CartItem> getAll();
}
