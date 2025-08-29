package za.co.admatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Wishlist> create(@RequestBody Wishlist wishlist) {
        try{
            Wishlist createdWishlist = service.create(wishlist);
            return ResponseEntity.ok(createdWishlist);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/read/{wishlistId}")
    public ResponseEntity<?> read(@PathVariable Long wishlistId) {
        try{
            Wishlist wishlist = service.read(wishlistId);
            return wishlist != null ? ResponseEntity.ok(wishlist) : ResponseEntity.notFound().build();
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to read wishlist: " + e.getMessage());
        }
    }

    @PutMapping("/update/{wishlistId}")
    public ResponseEntity<?> update(@RequestBody Wishlist wishlist) {
        try{
            Wishlist updatedWishlistWithId = new Wishlist.Builder()
                    .copy(wishlist)
                    .setWishlistId(wishlist.getWishlistId())
                    .build();
            Wishlist updatedWishlist = service.update(updatedWishlistWithId);
            return updatedWishlist != null ? ResponseEntity.ok(updatedWishlist) : ResponseEntity.notFound().build();
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update wishlist: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{wishlistID}")
    public ResponseEntity<?> delete(@PathVariable Long wishlistID) {
        try{
            boolean deleted = service.delete(wishlistID);
            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Failed to delete wishlist :" + e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public Iterable<Wishlist> getAll() {
        return service.getAll();
    }
}
