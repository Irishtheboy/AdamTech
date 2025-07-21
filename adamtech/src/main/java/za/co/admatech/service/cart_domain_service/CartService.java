/*





CartService.java



Author: Teyana Raubenheimer (230237622)



Date: 24 May 2025 */ package za.co.admatech.service.cart_domain_service;

import jakarta.persistence.EntityNotFoundException; import jakarta.transaction.Transactional; import org.springframework.stereotype.Service; import za.co.admatech.domain.Cart; import za.co.admatech.repository.CartRepository; import za.co.admatech.util.Helper;

import java.util.List;

@Service public class CartService implements ICartService { private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    @Transactional
    public Cart create(Cart cart) {
        if (!Helper.isValidCart(cart)) {
            throw new IllegalArgumentException("Invalid cart data");
        }
        return cartRepository.save(cart);
    }

    @Override
    public Cart read(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Cart with ID " + cartId + " not found"));
    }

    @Override
    @Transactional
    public Cart update(Cart cart) {
        if (!Helper.isValidCart(cart) || cart.getCartID() == null) {
            throw new IllegalArgumentException("Invalid cart data or missing ID");
        }
        if (!cartRepository.existsById(cart.getCartID())) {
            throw new EntityNotFoundException("Cart with ID " + cart.getCartID() + " not found");
        }
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public boolean delete(Long cartId) {
        if (!cartRepository.existsById(cartId)) {
            return false;
        }
        cartRepository.deleteById(cartId);
        return true;
    }

    @Override
    public List<Cart> getAll() {
        return cartRepository.findAll();
    }

}