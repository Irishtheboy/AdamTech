/*CartItemService.java
  Author: Teyana Raubenheimer (230237622)
  Date: 23 May 2025
  Updated: 19 Sep 2025
  Description: Populates full Product in CartItem
*/

package za.co.admatech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.admatech.domain.CartItem;
import za.co.admatech.domain.Product;
import za.co.admatech.repository.CartItemRepository;
import za.co.admatech.repository.ProductRepository;

import java.util.List;

@Service
public class CartItemService implements ICartItemService {

    private final CartItemRepository repository;
    private final ProductRepository productRepository;

    @Autowired
    public CartItemService(CartItemRepository repository, ProductRepository productRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
    }

    @Override
    public CartItem create(CartItem cartItem) {
        populateProduct(cartItem);
        return repository.save(cartItem);
    }

    @Override
    public CartItem read(Long id) {
        CartItem item = repository.findById(id).orElse(null);
        if (item != null) populateProduct(item);
        return item;
    }

    @Override
    public CartItem update(CartItem cartItem) {
        populateProduct(cartItem);
        return repository.save(cartItem);
    }

    @Override
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<CartItem> getAll() {
        List<CartItem> items = repository.findAll();
        items.forEach(this::populateProduct);
        return items;
    }

    // Helper method to fetch full Product
    private void populateProduct(CartItem cartItem) {
        if (cartItem.getProduct() != null && cartItem.getProduct().getProductId() != null) {
            Product fullProduct = productRepository.findById(cartItem.getProduct().getProductId())
                    .orElse(null);
            cartItem.setProduct(fullProduct);
        }
    }
}
