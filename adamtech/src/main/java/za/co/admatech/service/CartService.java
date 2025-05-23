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
    public Cart read(String s) {
        return this.repository.findById(s).orElse(null);
    }

    @Override
    public Cart update(Cart cart) {
        return this.repository.save(cart);
    }

    @Override
    public boolean delete(String s) {
        this.repository.deleteById(s);
        return true;
    }

    @Override
    public List<Cart> getCarts() {
        return this.repository.findAll();
    }
}
