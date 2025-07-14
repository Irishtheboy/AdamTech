/*ICartService.java
  Author: Teyana Raubenheimer (230237622)
  Date: 24 May 2025
 */

package za.co.admatech.service.cart_domain_service;

import za.co.admatech.domain.Cart;
import za.co.admatech.service.IService;

import java.util.List;

public interface ICartService extends IService<Cart, String> {
    List<Cart> getAll();
}
