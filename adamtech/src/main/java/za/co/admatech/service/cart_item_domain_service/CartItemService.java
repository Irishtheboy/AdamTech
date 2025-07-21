/*





CartItemService.java



Author: Teyana Raubenheimer (230237622)



Date: 23 May 2025 */ package za.co.admatech.service.cart_item_domain_service;

import jakarta.persistence.EntityNotFoundException; import jakarta.transaction.Transactional; import org.springframework.stereotype.Service; import za.co.admatech.domain.CartItem; import za.co.admatech.repository.CartItemRepository; import za.co.admatech.util.Helper;

import java.util.List;

@Service public class CartItemService implements ICartItemService { private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    @Transactional
    public CartItem create(CartItem cartItem) {
        if (cartItem == null || cartItem.getProduct() == null || cartItem.getCart() == null || cartItem.getQuantity() <= 0) {
            throw new IllegalArgumentException("Invalid cart item data");
        }
        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem read(Long id) {
        return cartItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CartItem with ID " + id + " not found"));
    }

    @Override
    @Transactional
    public CartItem update(CartItem cartItem) {
        if (cartItem == null || cartItem.getCartItemID() == null || cartItem.getProduct() == null || cartItem.getCart() == null || cartItem.getQuantity() <= 0) {
            throw new IllegalArgumentException("Invalid cart item data or missing ID");
        }
        if (!cartItemRepository.existsById(cartItem.getCartItemID())) {
            throw new EntityNotFoundException("CartItem with ID " + cartItem.getCartItemID() + " not found");
        }
        return cartItemRepository.save(cartItem);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        if (!cartItemRepository.existsById(id)) {
            return false;
        }
        cartItemRepository.deleteById(id);
        return true;
    }

    @Override
    public List<CartItem> getAll() {
        return cartItemRepository.findAll();
    }

}