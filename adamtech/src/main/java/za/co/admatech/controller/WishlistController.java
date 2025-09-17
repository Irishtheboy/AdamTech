package za.co.admatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import za.co.admatech.domain.Wishlist;
import za.co.admatech.service.WishlistService;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService service;
    @Autowired
    public WishlistController(WishlistService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Wishlist create(@RequestBody Wishlist wishlist) {
        return service.create(wishlist); // return saved entity with ID
    }


    @GetMapping("/read/{wishlistID}")
    public Wishlist read(@PathVariable Long wishlistID) {
        return service.read(wishlistID);
    }

    @PutMapping("/update")
    public Wishlist update(@RequestBody Wishlist wishlist) {
        return service.update(wishlist);
    }

    @DeleteMapping("/delete/{wishlistID}")
    public boolean delete(@PathVariable Long wishlistID) {
        return service.delete(wishlistID);
    }

    @GetMapping("/getAll")
    public Iterable<Wishlist> getAll() {
        return service.getAll();
    }
}
