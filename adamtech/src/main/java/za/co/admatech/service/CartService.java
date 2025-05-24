package za.co.admatech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.admatech.domain.Cart;
import za.co.admatech.repository.CartRepository;

import java.util.List;

@Service
public class CartService implements ICartService {

    private static ICartService service;

    @Autowired
    private CartRepository repository;


    @Override
    public Cart create(Cart cart) {
        return this.repository.save(cart);
    }

    @Override
    public Cart read(String id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public Cart update(Cart cart) {
        return this.repository.save(cart);
    }

    @Override
    public boolean delete(String id) {
        this.repository.deleteById(id);
        return true;
    }

    @Override
    public List<Cart> getAll() {
        return this.repository.findAll();
    }
}
