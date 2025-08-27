package za.co.admatech.service;

import za.co.admatech.domain.Wishlist;
import java.util.List;

public interface IWishlistService extends IService<Wishlist, Long> {
    List<Wishlist> findByCustomerId(Long customerId);
    List<Wishlist> findByProductId(Long productId);
}
