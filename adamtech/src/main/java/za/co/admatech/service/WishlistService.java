package za.co.admatech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.admatech.domain.Wishlist;
import za.co.admatech.repository.WishlistRepository;

import java.util.List;

@Service
public class WishlistService implements IWishlistService {
    public final WishlistRepository WishlistRepository;

    @Autowired
    public WishlistService(WishlistRepository WishlistRepository) {
        this.WishlistRepository = WishlistRepository;
    }

    @Override
    public Wishlist create(Wishlist wishlist) {
        return this.WishlistRepository.save(wishlist);
    }

    @Override
    public Wishlist read(Long id) {
        return this.WishlistRepository.findById(id).orElse(null);
    }

    @Override
    public Wishlist update(Wishlist wishlist) {
        return this.WishlistRepository.save(wishlist);
    }

    @Override
    public boolean delete(Long id) {
        this.WishlistRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Wishlist> getAll() {
        return this.WishlistRepository.findAll();
    }

}
