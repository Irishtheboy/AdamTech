package za.co.admatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("isAuthenticated()") // ✅ Only authenticated users
    public Wishlist create(@RequestBody Wishlist wishlist) {
        return service.create(wishlist);
    }

    @GetMapping("/read/{wishlistID}")
    @PreAuthorize("isAuthenticated()") // ✅ Only authenticated users
    public Wishlist read(@PathVariable Long wishlistID) {
        return service.read(wishlistID);
    }

    @PutMapping("/update")
    @PreAuthorize("isAuthenticated()") // ✅ Only authenticated users
    public Wishlist update(@RequestBody Wishlist wishlist) {
        return service.update(wishlist);
    }

    @DeleteMapping("/delete/{wishlistID}")
    @PreAuthorize("isAuthenticated()") // ✅ Only authenticated users
    public boolean delete(@PathVariable Long wishlistID) {
        return service.delete(wishlistID);
    }

    @GetMapping("/getAll")
    @PreAuthorize("isAuthenticated()") // ✅ Only authenticated users
    public Iterable<Wishlist> getAll() {
        return service.getAll();
    }
}