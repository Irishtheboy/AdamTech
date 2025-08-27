package za.co.admatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.domain.Wishlist;
import za.co.admatech.service.WishlistService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    
    private final WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("/create")
    public ResponseEntity<Wishlist> create(@RequestBody Wishlist wishlist) {
        Wishlist savedWishlist = wishlistService.create(wishlist);
        return ResponseEntity.ok(savedWishlist);
    }

    @GetMapping("/read/{wishlistId}")
    public ResponseEntity<Wishlist> read(@PathVariable Long wishlistId) {
        Wishlist wishlist = wishlistService.read(wishlistId);
        return wishlist != null ? ResponseEntity.ok(wishlist) : ResponseEntity.notFound().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Wishlist> update(@RequestBody Wishlist wishlist) {
        Wishlist updatedWishlist = wishlistService.update(wishlist);
        return ResponseEntity.ok(updatedWishlist);
    }

    @DeleteMapping("/delete/{wishlistId}")
    public ResponseEntity<Boolean> delete(@PathVariable Long wishlistId) {
        boolean deleted = wishlistService.delete(wishlistId);
        return ResponseEntity.ok(deleted);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Wishlist>> getAll() {
        List<Wishlist> wishlists = wishlistService.getAll();
        return ResponseEntity.ok(wishlists);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Wishlist>> getByCustomerId(@PathVariable Long customerId) {
        List<Wishlist> wishlists = wishlistService.findByCustomerId(customerId);
        return ResponseEntity.ok(wishlists);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Wishlist>> getByProductId(@PathVariable Long productId) {
        List<Wishlist> wishlists = wishlistService.findByProductId(productId);
        return ResponseEntity.ok(wishlists);
    }
}
