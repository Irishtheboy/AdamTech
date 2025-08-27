package za.co.admatech.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.admatech.domain.Customer;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Product;
import za.co.admatech.domain.Wishlist;
import za.co.admatech.factory.CustomerFactory;
import za.co.admatech.factory.MoneyFactory;
import za.co.admatech.factory.ProductFactory;
import za.co.admatech.factory.WishlistFactory;
import za.co.admatech.repository.WishlistRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WishlistServiceTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private WishlistService wishlistService;

    private Wishlist testWishlist;
    private Customer testCustomer;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        testCustomer = CustomerFactory.createCustomer(
                "John", "Doe", "john.doe@example.com", null
        );

        Money testPrice = MoneyFactory.createMoney(150000, "ZAR");
        testProduct = ProductFactory.createProduct(
                "HP Victus 15", "Gaming Laptop", "CAT90",
                testPrice, "electronics"
        );

        testWishlist = WishlistFactory.createWishlist(testCustomer, testProduct, LocalDateTime.now());
    }

    @Test
    void create_ShouldReturnSavedWishlist_WhenValidWishlist() {
        // Given
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(testWishlist);

        // When
        Wishlist result = wishlistService.create(testWishlist);

        // Then
        assertNotNull(result);
        assertEquals(testWishlist.getCustomer().getCustomerId(), result.getCustomer().getCustomerId());
        assertEquals(testWishlist.getProduct().getProductId(), result.getProduct().getProductId());
        verify(wishlistRepository).save(testWishlist);
    }

    @Test
    void read_ShouldReturnWishlist_WhenWishlistExists() {
        // Given
        when(wishlistRepository.findById(1L)).thenReturn(Optional.of(testWishlist));

        // When
        Wishlist result = wishlistService.read(1L);

        // Then
        assertNotNull(result);
        assertEquals(testWishlist.getCustomer().getCustomerId(), result.getCustomer().getCustomerId());
        assertEquals(testWishlist.getProduct().getProductId(), result.getProduct().getProductId());
        verify(wishlistRepository).findById(1L);
    }

    @Test
    void read_ShouldReturnNull_WhenWishlistDoesNotExist() {
        // Given
        when(wishlistRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When
        Wishlist result = wishlistService.read(999L);

        // Then
        assertNull(result);
        verify(wishlistRepository).findById(999L);
    }

    @Test
    void delete_ShouldReturnTrue_WhenWishlistExists() {
        // Given
        when(wishlistRepository.existsById(1L)).thenReturn(true);
        doNothing().when(wishlistRepository).deleteById(1L);

        // When
        boolean result = wishlistService.delete(1L);

        // Then
        assertTrue(result);
        verify(wishlistRepository).existsById(1L);
        verify(wishlistRepository).deleteById(1L);
    }

    @Test
    void delete_ShouldReturnFalse_WhenWishlistDoesNotExist() {
        // Given
        when(wishlistRepository.existsById(999L)).thenReturn(false);

        // When
        boolean result = wishlistService.delete(999L);

        // Then
        assertFalse(result);
        verify(wishlistRepository).existsById(999L);
        verify(wishlistRepository, never()).deleteById(anyLong());
    }

    @Test
    void findByCustomerId_ShouldReturnWishlistItems_WhenCustomerHasWishlistItems() {
        // Given
        List<Wishlist> wishlists = Arrays.asList(testWishlist);
        when(wishlistRepository.findByCustomer_CustomerId(1L)).thenReturn(wishlists);

        // When
        List<Wishlist> result = wishlistService.findByCustomerId(1L);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testWishlist.getCustomer().getCustomerId(), result.get(0).getCustomer().getCustomerId());
        verify(wishlistRepository).findByCustomer_CustomerId(1L);
    }

    @Test
    void findByProductId_ShouldReturnWishlistItems_WhenProductIsInWishlists() {
        // Given
        List<Wishlist> wishlists = Arrays.asList(testWishlist);
        when(wishlistRepository.findByProduct_ProductId(1L)).thenReturn(wishlists);

        // When
        List<Wishlist> result = wishlistService.findByProductId(1L);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testWishlist.getProduct().getProductId(), result.get(0).getProduct().getProductId());
        verify(wishlistRepository).findByProduct_ProductId(1L);
    }

    @Test
    void getAll_ShouldReturnListOfWishlists() {
        // Given
        List<Wishlist> wishlists = Arrays.asList(testWishlist);
        when(wishlistRepository.findAll()).thenReturn(wishlists);

        // When
        List<Wishlist> result = wishlistService.getAll();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testWishlist.getCustomer().getCustomerId(), result.get(0).getCustomer().getCustomerId());
        verify(wishlistRepository).findAll();
    }
}
