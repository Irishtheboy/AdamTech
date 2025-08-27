package za.co.admatech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.admatech.domain.Wishlist;
import za.co.admatech.repository.WishlistRepository;

import java.util.List;

@Service
public class WishlistService implements IWishlistService {

    private final WishlistRepository wishlistRepository;

    @Autowired
    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    @Override
    public Wishlist create(Wishlist wishlist) {
        return wishlistRepository.save(wishlist);
    }

    @Override
    public Wishlist read(Long wishlistId) {
        return wishlistRepository.findById(wishlistId).orElse(null);
    }

    @Override
    public Wishlist update(Wishlist wishlist) {
        return wishlistRepository.save(wishlist);
    }

    @Override
    public List<Wishlist> getAll() {
        return wishlistRepository.findAll();
    }

    @Override
    public boolean delete(Long wishlistId) {
        if (!wishlistRepository.existsById(wishlistId)) return false;
        wishlistRepository.deleteById(wishlistId);
        return true;
    }

    @Override
    public List<Wishlist> findByCustomerId(Long customerId) {
        return wishlistRepository.findByCustomer_CustomerId(customerId);
    }

    @Override
    public List<Wishlist> findByProductId(Long productId) {
        return wishlistRepository.findByProduct_ProductId(productId);
    }
}
